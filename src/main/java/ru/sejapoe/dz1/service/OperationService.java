package ru.sejapoe.dz1.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sejapoe.dz1.model.Operation;
import ru.sejapoe.dz1.repo.OperationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OperationService {
    private final OperationRepository operationRepository;

    public Operation logOperation(Operation.EntityReference entityReference, Operation.OperationType operationType) {
        Operation operation = Operation.builder()
                .entityReference(entityReference)
                .type(operationType)
                .time(LocalDateTime.now())
                .build();
        Operation saved = operationRepository.save(operation);
        log.debug(saved.toString());
        return saved;
    }

    public List<Operation> getAll() {
        return operationRepository.findAll();
    }
}
