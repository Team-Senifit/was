package com.senifit.was.repository.user;

import com.senifit.was.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String id);
    Optional<User> findByUsername(String username);
}

