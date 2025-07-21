package org.ludito.app.rest.service.impl.auth;


import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.exceptions.InvalidTokenException;
import org.ludito.app.config.utils.GlobalVar;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.entity.auth.UserAccess;
import org.ludito.app.rest.enums.UserLoginStatus;
import org.ludito.app.rest.payload.res.auth.ResLoginVerify;
import org.ludito.app.rest.repository.auth.UserAccessRepository;
import org.ludito.app.rest.repository.auth.UserRepository;
import org.ludito.app.rest.service.auth.JwtService;
import org.ludito.app.rest.service.auth.TokenService;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements TokenService {

    private final UserAccessRepository accessRepo;
    private final UserRepository userRepo;
    private final JwtService jwtService;


    @Override
    public User validateToken(String token) {

        UUID uuid = validateJwtToken(token);
        User res = validateUser(uuid);

        if ((res.getAccess() == null) || res.getAccess().getStatus() == null || !UserLoginStatus.VERIFIED.equals(res.getAccess().getStatus())) {
            throw new InvalidTokenException("Невалидный токен");
        }

        return res;
    }


    @Override
    public ResLoginVerify refreshToken(String accessToken, String refreshToken, HttpServletResponse httpServletResponse) {

        Optional<UserAccess> accessOptional = this.accessRepo.findByAccessTokenAndRefreshToken(
                accessToken,
                refreshToken
        );

        if (accessOptional.isEmpty()) {
            throw new InvalidTokenException("Невалидный токен");
        }

        UUID userId = null;
        User user = null;
        try {
            userId = validateJwtToken(refreshToken);
            user = validateUser(userId);
        } catch (InvalidTokenException e) {
            throw new InvalidTokenException("Невалидный токен");
        }

        String newAccessToken = jwtService.generateAccessToken(Map.of("userId", user.getUuid()), user.getUsername());

        String newRefreshToken = jwtService.generateRefreshToken(Map.of("userId", user.getUuid()), user.getUsername());

        UserAccess adminUserAccess = accessOptional.get();
        adminUserAccess.setAccessToken(newAccessToken);
        adminUserAccess.setAccessTokenExpire(jwtService.getAccessExpiryDate(newAccessToken));

        adminUserAccess.setRefreshToken(newRefreshToken);
        adminUserAccess.setRefreshTokenExpire(jwtService.getAccessExpiryDate(newRefreshToken));

        this.accessRepo.saveAndFlush(adminUserAccess);

        return ResLoginVerify.of(adminUserAccess);
    }

    private UUID validateJwtToken(String token) {
        Claims claims = jwtService.validateAndGetClaims(token);
        String userId = String.valueOf(claims.get("userId"));
        GlobalVar.setUserUuid(UUID.fromString(userId));
        return UUID.fromString(userId);
    }

    private UUID getUserId(String token) {
        Claims claims = jwtService.getAccessClaims(token);
        String userId = claims.getSubject();

        GlobalVar.setUserUuid(UUID.fromString(userId));
        return GlobalVar.getUserUUID();
    }

    private User validateUser(UUID userId) {
        Optional<User> optional;
        try {
            optional = userRepo.findByUuid(userId);
        } catch (Exception e) {
            throw new InvalidTokenException("Невалидный токен");
        }

        if (optional.isEmpty()) {
            throw new InvalidTokenException("Пользователь не найден");
        }

        User authUser = optional.get();

        if (!accessRepo.existsByUser_Id(authUser.getId())) {
            throw new InvalidTokenException("Пользователь не авторизован");
        }
        return authUser;
    }


}
