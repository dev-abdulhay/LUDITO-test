package org.ludito.app.rest.payload.req.wallet;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqAddWallet {

    @NotNull
    private CurrencyEnum currency;

    @NotNull
    private String name;

}
