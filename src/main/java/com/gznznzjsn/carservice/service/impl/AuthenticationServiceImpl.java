package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.domain.AuthenticationRequest;
import com.gznznzjsn.carservice.domain.AuthenticationResponse;
import com.gznznzjsn.carservice.domain.RegisterRequest;
import com.gznznzjsn.carservice.domain.user.Role;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.service.AuthenticationService;
import com.gznznzjsn.carservice.service.JwtService;
import com.gznznzjsn.carservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userService.create(user);
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userService.getByEmail(request.getEmail());
        String jwt = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}
