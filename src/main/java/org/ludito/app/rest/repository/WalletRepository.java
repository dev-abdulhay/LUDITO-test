package org.ludito.app.rest.repository;

import org.ludito.app.config.base.BaseRepository;
import org.ludito.app.rest.entity.wallet.Wallet;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletRepository extends BaseRepository<Wallet> {
    List<Wallet> findAllByUser_Uuid(UUID userUuid);

    List<Wallet> findAllByUser_Id(Long userId);

    Boolean existsByUser_IdAndCurrency(Long userId, CurrencyEnum currency);

    Optional<Wallet> findByAccountNumber(String creditAccount);
}