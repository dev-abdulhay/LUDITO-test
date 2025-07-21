package org.ludito.app.rest.payload.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link Wallet}
 */
@AllArgsConstructor
@Getter
public class ResWalletDto implements Serializable {

    private final UUID uuid;

    private final Integer order;

    private final String name;

    private final String accountNumber;

    private final CurrencyEnum currency;

    private final Long balance;
}