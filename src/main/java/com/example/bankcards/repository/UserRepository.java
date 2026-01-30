package com.example.bankcards.repository;

import com.example.bankcards.entity.User;
import com.example.bankcards.entity.invariants.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsByEmail(String email);
    boolean existsByRole(Role role);
    Optional<User> findByEmail(String email);
    Page<User> findAll(Pageable pageable);
}
