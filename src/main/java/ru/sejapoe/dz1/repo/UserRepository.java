package ru.sejapoe.dz1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sejapoe.dz1.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}