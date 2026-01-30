package com.example.bankcards.dto.response;

import com.example.bankcards.entity.invariants.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private boolean enabled = true;
}
