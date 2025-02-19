package org.forum.repository;

import org.forum.model.entity.Activation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivationRepository extends JpaRepository<Activation, Long> {
    Optional<Activation> findByActivationCode(String token);
    List<Activation> findAllByExpiresAtBefore(LocalDateTime expiresAt);
}
