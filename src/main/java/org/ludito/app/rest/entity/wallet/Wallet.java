package org.ludito.app.rest.entity.wallet;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ludito.app.config.base.BaseEntity;
import org.ludito.app.config.base.BaseScheme;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet", schema = BaseScheme.BILLING)
public class Wallet extends BaseEntity {


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private CurrencyEnum currency;

    @Column(name = "balance")
    private Long balance;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
