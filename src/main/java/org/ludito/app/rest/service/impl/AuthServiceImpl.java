package org.ludito.app.rest.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ludito.app.config.exceptions.CustomException;
import org.ludito.app.config.utils.GlobalVar;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.entity.auth.UserAccess;
import org.ludito.app.rest.enums.UserLoginStatus;
import org.ludito.app.rest.payload.req.auth.ReqLogin;
import org.ludito.app.rest.payload.req.auth.ReqLoginVerify;
import org.ludito.app.rest.payload.res.auth.ResLogin;
import org.ludito.app.rest.payload.res.auth.ResLoginVerify;
import org.ludito.app.rest.payload.res.auth.ResLogout;
import org.ludito.app.rest.repository.auth.UserAccessRepository;
import org.ludito.app.rest.service.AuthService;
import org.ludito.app.rest.service.UserService;
import org.ludito.app.rest.service.auth.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserAccessRepository userAccessRepository;
    private final JwtService jwtService;

    @Override
    public ResLogin loginInit(ReqLogin req) {

        User user = userService.getByUsername(req.getUsername());

        if (!passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            throw new CustomException("Invalid credentials");
        }

        String otpCode = userService.generateOtpCode(user);

        UserAccess userAccess = UserAccess
                .builder()
                .status(UserLoginStatus.INIT)
                .user(user)
                .code(otpCode)
                .build();

        userAccessRepository.save(userAccess);

        return ResLogin.builder()
                .identity(userAccess.getUuid())
                .message("Login initialized successfully. Please verify your OTP code. (" + otpCode + ")")
                .build();
    }

    @Override
    public ResLoginVerify loginVerify(ReqLoginVerify req) {
        UserAccess userAccess = userAccessRepository.findByUuidAndStatus(req.getIdentity(), UserLoginStatus.INIT)
                .orElseThrow(() -> new CustomException("Invalid login session"));

        if (!userAccess.getCode().equals(req.getCode())) {
            throw new CustomException("Invalid OTP code");
        }

        User user = userAccess.getUser();
        String accessToken = jwtService.generateAccessToken(Map.of("userId", user.getUuid()), user.getUsername());
        String refreshToken = jwtService.generateRefreshToken(Map.of("userId", user.getUuid()), user.getUsername());
        LocalDateTime accessExpiryDate = jwtService.getAccessExpiryDate(accessToken);
        LocalDateTime refreshExpiryDate = jwtService.getRefreshExpiryDate(refreshToken);


        userAccess.setStatus(UserLoginStatus.VERIFIED);
        userAccessRepository.save(userAccess);

        return ResLoginVerify.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpire(accessExpiryDate)
                .refreshTokenExpire(refreshExpiryDate)
                .build();
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public ResLogout logout() {

//        List<UserAccess> allByUserIdAndStatus = userAccessRepository.findAllByUser_IdAndStatus(GlobalVar.getUserId(), UserLoginStatus.VERIFIED);
//         userAccessRepository.deleteByUser_IdAndStatus(GlobalVar.getUserId(), UserLoginStatus.VERIFIED);
//        userAccessRepository.deleteAll(allByUserIdAndStatus);
        User user = GlobalVar.getAuthUser();
        user.setAccess(null);
        userService.saveAndFlush(user);

        GlobalVar.clearContext();

        return ResLogout.builder()
                .message("Logout successfully")
                .build();
    }
}
