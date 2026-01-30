package com.example.bankcards.service.impl;

import com.example.bankcards.dto.JwtAuthenticationResponse;
import com.example.bankcards.dto.SignInRequest;
import com.example.bankcards.security.components.BankUserDetailsService;
import com.example.bankcards.security.components.JwtService;
import com.example.bankcards.service.AuthenticationService;
import com.example.bankcards.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final BankUserDetailsService bankUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse getJwtAuthenticationResponse(String login) {
        UserDetails userDetails = bankUserDetailsService.loadUserByUsername(login);
        String jwtToken = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwtToken);
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        return getJwtAuthenticationResponse(request.getEmail());
    }
}
