package com.example.bankcards.service;

import com.example.bankcards.dto.JwtAuthenticationResponse;
import com.example.bankcards.dto.SignInRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse getJwtAuthenticationResponse(String login);
    JwtAuthenticationResponse signIn(SignInRequest request);
}
