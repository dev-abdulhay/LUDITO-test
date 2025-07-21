package org.ludito.app.rest.service.impl;

import lombok.RequiredArgsConstructor;
import org.ludito.app.config.base.BaseRepository;
import org.ludito.app.config.exceptions.CustomException;
import org.ludito.app.rest.entity.transaction.Operation;
import org.ludito.app.rest.enums.transaction.OperationStatus;
import org.ludito.app.rest.repository.transaction.OperationRepository;
import org.ludito.app.rest.service.OperationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository repository;

    @Override
    public BaseRepository<Operation> getRepository() {
        return repository;
    }

    @Override
    public Operation findByUuidAndStatus(UUID uuid, OperationStatus status) {
        return repository.findByUuidAndStatus(uuid, status)
                .orElseThrow(() -> new CustomException("Operation not found with UUID: " + uuid + " and status: " + status));
    }
}
