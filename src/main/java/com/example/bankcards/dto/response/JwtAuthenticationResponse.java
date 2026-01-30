package com.example.bankcards.dto.response;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@Service
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JwtAuthenticationResponse {
    private String token;
}
