package com.example.bankcards.service;

import com.example.bankcards.dto.ChangeUserRequest;
import com.example.bankcards.dto.CreateUserRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.invariants.Role;

public interface UserService {
    User createUser(CreateUserRequest request, Role role);
    void blockUser(Long id);
    User changeUser(ChangeUserRequest request);
    User getUserById(Long id);
    User getUserByEmail(String email);
    boolean existsByRole(Role role);
}
