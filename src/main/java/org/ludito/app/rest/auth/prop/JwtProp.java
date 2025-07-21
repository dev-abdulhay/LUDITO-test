package org.ludito.app.rest.auth.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProp {

    private String accessSecret;
    private String refreshSecret;
    private Long accessExpireTime;
    private Long refreshExpireDays;
}
