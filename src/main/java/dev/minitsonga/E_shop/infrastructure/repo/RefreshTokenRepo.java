package dev.minitsonga.E_shop.infrastructure.repo;

import dev.minitsonga.E_shop.domain.RefreshToken;
import dev.minitsonga.E_shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}