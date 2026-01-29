package com.example.bankcards.service;

import com.example.bankcards.dto.ChangeUserRequest;
import com.example.bankcards.dto.CreateUserRequest;
import com.example.bankcards.entity.User;

public interface UserService {
    User createUser(CreateUserRequest request);
    void blockUser(Long id);
    User changeUser(ChangeUserRequest request);
    User getUserById(Long id);
    User getUserByEmail(String email);
}
