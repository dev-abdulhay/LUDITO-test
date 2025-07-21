package org.ludito.app.rest.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.ludito.app.config.utils.GlobalVar;
import org.ludito.app.rest.auth.config.SecurityConfig;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.service.auth.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class BeforeFilter extends OncePerRequestFilter {

    private final TokenService extIdentityService;

    private final List<RequestMatcher> matchers = new ArrayList<>();

    public BeforeFilter(TokenService extIdentityService) {
        this.extIdentityService = extIdentityService;

        for (String permitUrl : SecurityConfig.PERMIT_URLS) {
            matchers.add(PathPatternRequestMatcher.withDefaults().matcher(permitUrl));
        }
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String authorization = request.getHeader("X-Auth-Token");


        GlobalVar.setAuthorization(authorization);

        if (!isPermitted(request)) {
            if (GlobalVar.getAuthorization() != null && !GlobalVar.getAuthorization().isBlank()) {
                try {
                    User user = extIdentityService.validateToken(GlobalVar.getAuthorization());

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user, "", List.of()
                    );
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

                    GlobalVar.setAuthUser(user);
                    GlobalVar.setUserUuid(user.getUuid());
                    GlobalVar.setUserId(user.getId());


                } catch (Exception e) {
                    this.exception(e.getMessage(), HttpStatus.UNAUTHORIZED, response);
                    return;
                }
            } else {
                this.exception("Не авторизован", HttpStatus.UNAUTHORIZED, response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void exception(String message, HttpStatus status, HttpServletResponse response) throws IOException {
        SecurityContextHolder.clearContext();
        GlobalVar.clearContext();
        response.setStatus(status.value());
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, ResponseEntity.status(status).body(message));
        out.flush();
    }

    private boolean isPermitted(HttpServletRequest request) {
        for (RequestMatcher matcher : matchers) {
            if (matcher.matches(request)) {
                return true;
            }
        }
        return false;
    }
}
