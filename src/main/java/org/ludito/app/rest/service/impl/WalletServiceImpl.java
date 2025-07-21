package org.ludito.app.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseRepository;
import org.ludito.app.config.exceptions.CustomException;
import org.ludito.app.config.utils.GlobalVar;
import org.ludito.app.rest.entity.auth.User;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.payload.req.wallet.ReqAddWallet;
import org.ludito.app.rest.repository.WalletRepository;
import org.ludito.app.rest.service.WalletService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    @Override
    public BaseRepository<Wallet> getRepository() {
        return repository;
    }

    @Override
    public List<Wallet> getAllByUser(Long userId) {
        return repository.findAllByUser_Id(userId);
    }

    @Override
    public Wallet getOne(UUID req) {
        return getByUuid(req);
    }

    @Override
    public Wallet add(ReqAddWallet req) {
        User user = GlobalVar.getAuthUser();

        Wallet wallet = new Wallet();
        wallet.setUser(user);
        wallet.setCurrency(req.getCurrency());
        wallet.setBalance(0L);
        wallet.setName(req.getName());
        save(wallet);

        wallet.setAccountNumber(String.format("%s_%s_%d_%d",
                wallet.getCurrency().name(),
                wallet.getCurrency().getCode(),
                wallet.getId(),
                user.getId()
        ));
        save(wallet);

        return wallet;
    }

    @Override
    public Wallet getByAccountNumber(String creditAccount) {
        return repository.findByAccountNumber(creditAccount)
                .orElseThrow(() -> new CustomException("Wallet not found for account number: " + creditAccount));
    }
}
