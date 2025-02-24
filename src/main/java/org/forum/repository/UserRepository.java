package org.forum.repository;

import org.forum.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
    List<User> findAllByActivatedFalseAndCreateDateBefore(LocalDateTime createDate);
    Page<User> findAllByUsernameContainingOrEmailContaining(String username, String email, Pageable pageable);
}
