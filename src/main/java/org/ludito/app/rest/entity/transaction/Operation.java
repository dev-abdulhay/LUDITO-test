package org.ludito.app.rest.entity.transaction;


import jakarta.persistence.*;
import lombok.*;
import org.ludito.app.config.base.BaseEntity;
import org.ludito.app.config.base.BaseScheme;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.enums.transaction.OperationStatus;
import org.ludito.app.rest.enums.transaction.OperationType;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operation", schema = BaseScheme.BILLING, indexes = {
        @Index(name = "idx_operation_uuid", columnList = "uuid")
})
public class Operation extends BaseEntity {


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OperationStatus status;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private OperationType type;


    @ManyToOne
    @JoinColumn(name = "credit_wallet")
    private Wallet creditWallet;


    @ManyToOne
    @JoinColumn(name = "debit_wallet")
    private Wallet debitWallet;


    @Column(name = "credit_amount")
    private Long creditAmount;

    @Column(name = "debit_amount")
    private Long debitAmount;

    @Column(name = "commission_amount")
    private Long commissionAmount;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @Column(name = "execute_date")
    LocalDateTime executeDate;

    private String description;

}
