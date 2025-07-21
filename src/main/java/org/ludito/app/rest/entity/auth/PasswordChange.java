package org.ludito.app.rest.entity.auth;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ludito.app.config.base.BaseEntity;
import org.ludito.app.config.base.BaseScheme;
import org.ludito.app.rest.enums.PasswordStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "password_reset", schema = BaseScheme.AUTH)
public class PasswordChange extends BaseEntity {


    @Column(name = "code")
    private String code;

    @Column(name = "code_expire")
    private LocalDateTime codeExpire;

    @Column(name = "try_count")
    private Short tryCount = 0;

    @Column(name = "is_reg")
    private Boolean isReg = Boolean.FALSE;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PasswordStatus status = PasswordStatus.CHECK;

    @Column(name = "status_message", columnDefinition = "TEXT")
    private String statusMessage;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
