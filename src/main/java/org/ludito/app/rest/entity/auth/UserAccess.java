package org.ludito.app.rest.entity.auth;

import jakarta.persistence.*;
import lombok.*;
import org.ludito.app.config.base.BaseEntity;
import org.ludito.app.config.base.BaseScheme;
import org.ludito.app.rest.enums.UserLoginStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_access", schema = BaseScheme.AUTH)
public class UserAccess extends BaseEntity {

    @Column(name = "refresh_token", unique = true, columnDefinition = "TEXT")
    private String refreshToken;

    @Column(name = "refresh_token_expire")
    private LocalDateTime refreshTokenExpire;

    @Column(name = "access_token", unique = true, columnDefinition = "TEXT")
    private String accessToken;

    @Column(name = "access_token_expire")
    private LocalDateTime accessTokenExpire;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserLoginStatus status;

    @Column(name = "code")
    private String code;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
