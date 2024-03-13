package ru.sejapoe.dz1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sejapoe.dz1.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
}