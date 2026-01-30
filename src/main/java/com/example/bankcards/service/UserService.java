package com.example.bankcards.service;

import com.example.bankcards.dto.request.ChangeUserRequest;
import com.example.bankcards.dto.request.CreateUserRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.invariants.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User createUser(CreateUserRequest request, Role role);
    void blockUser(Long id);
    User changeUser(ChangeUserRequest request);
    User getUserById(Long id);
    User getUserByEmail(String email);
    boolean existsByRole(Role role);
    List<User> getAllUsers(Pageable pageable);
}
