package org.ludito.app.rest.entity.auth;


import jakarta.persistence.*;
import lombok.*;
import org.ludito.app.config.base.BaseEntity;
import org.ludito.app.config.base.BaseScheme;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.enums.UserStatus;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user", schema = BaseScheme.AUTH)
@Builder
public class User extends BaseEntity {


    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.BLOCKED;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private UserAccess access;

    @OneToMany(mappedBy = "user")
    private List<Wallet> wallets;

}
