package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtServiceImpl implements JwtService {

    private static Key accessKey;
    private static Key refreshKey;

    @Value("${car-service.secrets.access-key}")
    public void setAccessKey(String key) {
        accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    @Value("${car-service.secrets.refresh-key}")
    public void setRefreshKey(String key) {
        refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateAccessToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .claim("role", userDetails.getAuthorities())
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts
                .builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isValidAccessToken(String token) {
        return isValidToken(token, accessKey);
    }

    @Override
    public boolean isValidRefreshToken(String token) {
        return isValidToken(token, refreshKey);
    }

    private boolean isValidToken(String token, Key key) {
        try {
            return extractClaim(token, Claims::getExpiration, key).after(new Date(System.currentTimeMillis()));
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public String extractUsernameFromAccessToken(String token) {
        return extractUsername(token, accessKey);
    }

    private String extractUsername(String token, Key key) {
        return extractClaim(token, Claims::getSubject, key);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver, Key key) {
        Claims claims = extractAllClaims(token, key);
        return claimsResolver.apply(claims);
    }

    @Override
    public String extractRefreshSubject(String token) {
        return extractClaim(token, Claims::getSubject, refreshKey);
    }


    private Claims extractAllClaims(String token, Key key) {
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
