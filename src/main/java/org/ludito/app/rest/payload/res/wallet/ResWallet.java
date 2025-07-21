package org.ludito.app.rest.payload.res.wallet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResWallet {

    private UUID id;

    private String name;

    private String accountNumber;

    private CurrencyEnum currency;

    private Long balance;


    public static ResWallet of(Wallet req) {
        ResWallet res = new ResWallet();
        res.setId(req.getUuid());
        res.setName(req.getName());
        res.setAccountNumber(req.getAccountNumber());
        res.setCurrency(req.getCurrency());
        res.setBalance(req.getBalance());
        return res;
    }

    public static List<ResWallet> of(List<Wallet> reqs) {
        return reqs.stream().map(ResWallet::of).toList();
    }

}
