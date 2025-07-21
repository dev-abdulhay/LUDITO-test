package org.ludito.app.rest.service;

import org.ludito.app.config.base.BaseService;
import org.ludito.app.rest.entity.transaction.OperationConfig;
import org.ludito.app.rest.enums.transaction.CurrencyEnum;
import org.ludito.app.rest.enums.transaction.OperationType;

public interface OperationConfigService extends BaseService<OperationConfig> {

   OperationConfig getOperationConfig(OperationType type, CurrencyEnum currency);

}
