package com.communityrideshare.auth.service;

import com.communityrideshare.auth.domain.User;
import com.communityrideshare.auth.repository.UserRepository;
import com.communityrideshare.auth.web.dtos.JwtAuthenticationResponse;
import com.communityrideshare.auth.web.dtos.LoginRequest;
import com.communityrideshare.auth.web.dtos.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signup(SignUpRequest request) {
        var user = new User();
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        var userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(), java.util.Collections.emptyList());
        var jwt = jwtService.generateToken(userDetails);
        // For simplicity, returning the same token as access and refresh token.
        // In a real-world scenario, the refresh token should be different and managed properly.
        return new JwtAuthenticationResponse(jwt, jwt);
    }

    public JwtAuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPasswordHash(), java.util.Collections.emptyList());
        var jwt = jwtService.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt, jwt);
    }
}
