package com.example.bankcards.service.impl;

import com.example.bankcards.dto.request.ChangeUserRequest;
import com.example.bankcards.dto.request.CreateUserRequest;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.invariants.Role;
import com.example.bankcards.exception.NotFoundException;
import com.example.bankcards.exception.UserAlreadyExistsException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User createUser(CreateUserRequest request, Role role) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new UserAlreadyExistsException("User with such an email already exists:"+request.getEmail());
        }

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .enabled(true)
                .build();

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void blockUser(Long id) {
        User user  = getUserById(id);
        user.setEnabled(false);
        userRepository.save(user);
    }

    @Override
    public User changeUser(ChangeUserRequest request) {
        User user = getUserById(request.getId());

        if (request.getEmail() != null
                && !request.getEmail().equals(user.getEmail())) {

            if (userRepository.existsByEmail(request.getEmail())) {
                throw new UserAlreadyExistsException(request.getEmail());
            }

            user.setEmail(request.getEmail());
        }

        if (request.getFirstName() != null) {
            user.setFirstName(request.getFirstName());
        }

        if (request.getLastName() != null) {
            user.setLastName(request.getLastName());
        }

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        if (request.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new NotFoundException("User with this number not found: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(()->new NotFoundException("User with this email not " +
                "found: " + email));
    }

    @Override
    public boolean existsByRole(Role role) {
        return userRepository.existsByRole(role);
    }

    @Override
    public List<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable).getContent();
    }
}
