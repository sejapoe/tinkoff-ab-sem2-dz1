package ru.sejapoe.dz1.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.sejapoe.dz1.model.Operation;

public interface OperationRepository extends MongoRepository<Operation, String> {
}
