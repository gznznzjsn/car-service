package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.domain.user.AuthEntity;
import com.gznznzjsn.carservice.domain.user.Role;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.service.AuthenticationService;
import com.gznznzjsn.carservice.service.JwtService;
import com.gznznzjsn.carservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
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
        String accessJwt = jwtService.generateAccessToken(user);
        String refreshJwt = jwtService.generateRefreshToken(user);
        return AuthEntity.builder()
                .accessToken(accessJwt)
                .refreshToken(refreshJwt)
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
        String accessJwt = jwtService.generateAccessToken(user);
        String refreshJwt = jwtService.generateRefreshToken(user);
        return AuthEntity.builder()
                .accessToken(accessJwt)
                .refreshToken(refreshJwt)
                .build();
    }

    @Override
    public AuthEntity refresh(AuthEntity authEntity) {
        String refreshToken = authEntity.getRefreshToken();
        if (!jwtService.isValidRefreshToken(refreshToken)) {
            throw new AccessDeniedException("Access denied!");
        }
        String email = jwtService.extractRefreshSubject(refreshToken);
        User user = userService.getByEmail(email);
        String accessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        return AuthEntity.builder()
                .accessToken(accessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

}
