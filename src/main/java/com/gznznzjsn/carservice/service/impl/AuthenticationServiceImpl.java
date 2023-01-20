package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.domain.user.AuthEntity;
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
    public AuthEntity register(AuthEntity authEntity) {
        User user = User.builder()
                .name(authEntity.getName())
                .email(authEntity.getEmail())
                .password(passwordEncoder.encode(authEntity.getPassword()))
                .role(Role.USER)
                .build();
        userService.create(user);
        String jwt = jwtService.generateToken(user);
        return AuthEntity.builder()
                .token(jwt)
                .build();
    }

    @Override
    public AuthEntity authenticate(AuthEntity authEntity) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authEntity.getEmail(),
                        authEntity.getPassword()
                )
        );
        User user = userService.getByEmail(authEntity.getEmail());
        String jwt = jwtService.generateToken(user);
        return AuthEntity.builder()
                .token(jwt)
                .build();
    }
}
