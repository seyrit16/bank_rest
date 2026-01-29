package com.example.bankcards.dto;

import com.example.bankcards.entity.invariants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChangeUserRequest {
    @NotNull
    private Long id;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private Role role;

    @Size(min = 8)
    private String password;
}
