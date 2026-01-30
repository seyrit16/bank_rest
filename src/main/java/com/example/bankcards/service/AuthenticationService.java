package com.example.bankcards.service;

import com.example.bankcards.dto.response.JwtAuthenticationResponse;
import com.example.bankcards.dto.request.SignInRequest;

public interface AuthenticationService {
    JwtAuthenticationResponse getJwtAuthenticationResponse(String login);
    JwtAuthenticationResponse signIn(SignInRequest request);
}
