package org.ludito.app.rest.service;

import org.ludito.app.config.base.BaseService;
import org.ludito.app.rest.entity.transaction.Operation;
import org.ludito.app.rest.enums.transaction.OperationStatus;

import java.util.UUID;

public interface OperationService extends BaseService<Operation> {

    Operation findByUuidAndStatus(UUID uuid, OperationStatus status);

}
