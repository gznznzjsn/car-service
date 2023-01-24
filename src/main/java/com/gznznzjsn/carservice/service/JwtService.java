package com.gznznzjsn.carservice.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {

    String generateAccessToken(UserDetails userDetails);

    String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateRefreshToken(UserDetails userDetails);

    boolean isValidAccessToken(String token);

    boolean isValidRefreshToken(String token);

    String extractUsernameFromAccessToken(String token);

    String extractRefreshSubject(String token);

}
