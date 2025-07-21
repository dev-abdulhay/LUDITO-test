package org.ludito.app.rest.service.impl.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ludito.app.config.exceptions.CustomException;
import org.ludito.app.config.utils.CoreUtils;
import org.ludito.app.rest.auth.prop.JwtProp;
import org.ludito.app.rest.service.auth.JwtService;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {


    private final JwtProp jwtProp;


    public String generateAccessToken(Map<String, Object> extraClaims, String username) {

        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now
                .plusMinutes(
                        Objects.requireNonNullElse(jwtProp.getAccessExpireTime(), 120L)
                )
                .atZone(ZoneId.systemDefault())
                .toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(accessExpiration)
                .addClaims(extraClaims)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProp.getAccessSecret())))
                .compact();
    }

    public String generateRefreshToken(Map<String, Object> extraClaims, String username) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(
                        Objects.requireNonNullElse(jwtProp.getRefreshExpireDays(), 30L)
                )
                .atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(refreshExpiration)
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProp.getRefreshSecret())))
                .compact();
    }

    public boolean validateAccessToken(@NonNull String accessToken) {
        return this.validateToken(accessToken, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProp.getAccessSecret())));
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProp.getRefreshSecret())));
    }

    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.
                    parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception expEx) {
            return false;
        }
    }

    public Claims getAccessClaims(@NonNull String token) {
        return this.getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProp.getAccessSecret())));
    }

    @Override
    public Claims validateAndGetClaims(String accessToken) {
        boolean validateAccessToken = this.validateAccessToken(accessToken);
        if (!validateAccessToken) {
            throw new CustomException("JWT token is invalid or expired");
        }

        return getAccessClaims(accessToken);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return this.getClaims(token, Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProp.getRefreshSecret())));
    }

    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        try {
            return
                    Jwts.
                            parserBuilder()
                            .setSigningKey(secret)
                            .build()
                            .parseClaimsJws(token)
                            .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public LocalDateTime getAccessExpiryDate(String token) {
        Claims claims = getAccessClaims(token);
        Date expiration = claims.getExpiration();
        return expiration
                .toInstant()
                .atZone(CoreUtils.getZoneId())
                .toLocalDateTime();
    }


    @Override
    public LocalDateTime getRefreshExpiryDate(String token) {
        Claims claims = getRefreshClaims(token);
        Date expiration = claims.getExpiration();
        return expiration
                .toInstant()
                .atZone(CoreUtils.getZoneId())
                .toLocalDateTime();
    }

}
