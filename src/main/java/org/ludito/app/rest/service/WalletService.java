package org.ludito.app.rest.service;

import org.ludito.app.config.base.BaseService;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.payload.req.ReqUUID;
import org.ludito.app.rest.payload.req.wallet.ReqAddWallet;

import java.util.List;
import java.util.UUID;

public interface WalletService extends BaseService<Wallet> {

     List<Wallet> getAllByUser(Long userId);

    Wallet getOne(UUID req);

    Wallet add(ReqAddWallet req);

    Wallet getByAccountNumber(String creditAccount);
}
