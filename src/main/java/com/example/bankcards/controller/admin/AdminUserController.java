package com.example.bankcards.controller.admin;

import com.example.bankcards.dto.request.ChangeUserRequest;
import com.example.bankcards.dto.request.CreateUserRequest;
import com.example.bankcards.dto.response.UserResponse;
import com.example.bankcards.entity.User;
import com.example.bankcards.entity.invariants.Role;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/user")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody CreateUserRequest request) {
        User user = userService.createUser(request, Role.ROLE_USER);
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
        return ResponseEntity
                .ok()
                .body(userResponse);
    }

    @PatchMapping("/block")
    public ResponseEntity<Void> blockUser(@RequestParam Long userId) {
        userService.blockUser(userId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/change")
    public ResponseEntity<UserResponse> changeUser(@RequestBody ChangeUserRequest request) {
        User user = userService.changeUser(request);
        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .enabled(user.isEnabled())
                .build();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> allUsers(@RequestParam int page, @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<User> users = userService.getAllUsers(pageable);
        List<UserResponse> usersResponse = users.stream()
                .map(user
                        -> new UserResponse(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getRole(),
                        user.isEnabled()
                ))
                .toList();

        return ResponseEntity
                .ok()
                .body(usersResponse);
    }
}
