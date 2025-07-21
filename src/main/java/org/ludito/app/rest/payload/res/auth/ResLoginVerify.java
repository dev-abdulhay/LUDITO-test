package org.ludito.app.rest.payload.res.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ludito.app.rest.entity.auth.UserAccess;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResLoginVerify {

    private String accessToken;

    private String refreshToken;

    private LocalDateTime accessTokenExpire;

    private LocalDateTime refreshTokenExpire;

    public static ResLoginVerify of(UserAccess adminUserAccess) {
        ResLoginVerify res = new ResLoginVerify();
        res.setAccessToken(adminUserAccess.getAccessToken());
        res.setRefreshToken(adminUserAccess.getRefreshToken());
        res.setAccessTokenExpire(adminUserAccess.getAccessTokenExpire());
        res.setRefreshTokenExpire(adminUserAccess.getRefreshTokenExpire());
        return res;
    }
}
