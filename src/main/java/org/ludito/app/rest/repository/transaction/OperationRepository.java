package org.ludito.app.rest.repository.transaction;

import org.ludito.app.config.base.BaseRepository;
import org.ludito.app.rest.entity.transaction.Operation;
import org.ludito.app.rest.enums.transaction.OperationStatus;

import java.util.Optional;
import java.util.UUID;

public interface OperationRepository extends BaseRepository<Operation> {

    Optional<Operation> findByUuidAndStatus(UUID uuid, OperationStatus status);

}