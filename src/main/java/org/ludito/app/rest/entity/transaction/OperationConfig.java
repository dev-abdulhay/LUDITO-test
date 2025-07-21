package org.ludito.app.rest.entity.transaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ludito.app.config.base.BaseEntity;
import org.ludito.app.config.base.BaseScheme;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;
import org.ludito.app.rest.enums.transaction.OperationType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "operation_config", schema = BaseScheme.INFO, uniqueConstraints = @UniqueConstraint(columnNames = {"type", "currency"}))
public class OperationConfig extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private OperationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyEnum currency;

    @Column(name = "commission_percent")
    private Long commissionPercent;

    @Column(name = "min_amount")
    private Long minAmount;

    @Column(name = "max_amount")
    private Long maxAmount;


}
