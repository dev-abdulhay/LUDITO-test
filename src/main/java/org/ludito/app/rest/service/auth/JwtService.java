package org.ludito.app.rest.service.auth;


import io.jsonwebtoken.Claims;

import java.time.LocalDateTime;
import java.util.Map;

public interface JwtService {

    String generateAccessToken(Map<String, Object> extraClaims, String username);

    String generateRefreshToken(Map<String, Object> extraClaims, String username);

    boolean validateAccessToken(String token);

    boolean validateRefreshToken(String token);

    Claims getAccessClaims(String token);

    Claims getRefreshClaims(String token);

    Claims validateAndGetClaims(String token);

    LocalDateTime getAccessExpiryDate(String token);

    LocalDateTime getRefreshExpiryDate(String token);
}
