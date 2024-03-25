package ru.sejapoe.dz1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sejapoe.dz1.model.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByStoredName(String storedName);
}
