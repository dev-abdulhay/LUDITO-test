package org.ludito.app.rest.repository.transaction;

import org.ludito.app.config.base.BaseRepository;
import org.ludito.app.rest.entity.transaction.OperationConfig;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;
import org.ludito.app.rest.enums.transaction.OperationType;

import java.util.Optional;

public interface OperationConfigRepository extends BaseRepository<OperationConfig> {

    Optional<OperationConfig> findByTypeAndCurrency(OperationType type, CurrencyEnum currency);

}