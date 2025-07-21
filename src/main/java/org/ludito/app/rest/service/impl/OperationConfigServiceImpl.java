package org.ludito.app.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseRepository;
import org.ludito.app.rest.entity.transaction.OperationConfig;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;
import org.ludito.app.rest.enums.transaction.OperationType;
import org.ludito.app.rest.repository.transaction.OperationConfigRepository;
import org.ludito.app.rest.service.OperationConfigService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OperationConfigServiceImpl implements OperationConfigService {

    private final OperationConfigRepository repository;

    @Override
    public BaseRepository<OperationConfig> getRepository() {
        return repository;
    }


    @Override
    public OperationConfig getOperationConfig(OperationType type, CurrencyEnum currency) {

        OperationConfig byTypeAndCurrency = repository.findByTypeAndCurrency(type, currency)
                .orElse(new OperationConfig(
                        type,
                        currency,
                        1L,
                        1_000_00L,
                        1_000_000_00L
                ));

        return byTypeAndCurrency;
    }
}
