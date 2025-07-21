package org.ludito.app.rest.service.auth;


import jakarta.servlet.http.HttpServletResponse;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.payload.res.auth.ResLoginVerify;


public interface TokenService {

    User validateToken(String token);

    ResLoginVerify refreshToken(
            String accessToken,
            String refreshToken,
            HttpServletResponse httpServletResponse
    );


}

