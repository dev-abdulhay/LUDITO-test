package org.ludito.app.rest.auth.config;

import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseURI;
import org.ludito.app.config.exceptions.InvalidTokenException;
import org.ludito.app.rest.auth.filter.BeforeFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] PERMIT_URLS = new String[]{
            BaseURI.DOC_OPEN_API,
            BaseURI.DOC_OPEN_API_,
            BaseURI.DOC_SWAGGER_API,
            BaseURI.DOC_SWAGGER_UI,
            BaseURI.DOC_SWAGGER_UI_,
            BaseURI.API1 + BaseURI.AUTH + BaseURI.REGISTER,
            BaseURI.API1 + BaseURI.AUTH + BaseURI.LOGIN,
            BaseURI.API1 + BaseURI.AUTH + BaseURI.LOGIN + BaseURI.VERIFY


    };

    private final PasswordEncoder passwordEncoder;

    private final BeforeFilter beforeFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PERMIT_URLS).permitAll()
                .anyRequest().authenticated()
        );

        http.sessionManagement(e -> e.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(this.beforeFilter, UsernamePasswordAuthenticationFilter.class);
        // disable form login
        http.httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            throw new InvalidTokenException("User not found: " + username);
        };
    }


}
