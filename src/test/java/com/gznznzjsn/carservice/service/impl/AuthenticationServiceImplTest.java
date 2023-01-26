package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.domain.exception.UniqueResourceException;
import com.gznznzjsn.carservice.domain.user.AuthEntity;
import com.gznznzjsn.carservice.domain.user.Role;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.service.UserService;
import com.gznznzjsn.carservice.web.security.JwtManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtManager jwtManager;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    public void registerAlreadyRegistered() {
        AuthEntity authEntity = AuthEntity.builder()
                .name("userName")
                .email("e@mail.com")
                .password("password")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        User user = User.builder()
                .name("userName")
                .email("e@mail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(userService.create(user)).thenThrow(UniqueResourceException.class);

        assertThrows(UniqueResourceException.class, () -> authenticationService.register(authEntity));

        verify(userService).create(user);
        verify(passwordEncoder).encode("password");
        verifyNoInteractions(jwtManager);
    }

    @Test
    public void registerCorrect() {
        AuthEntity authEntity = AuthEntity.builder()
                .name("userName")
                .email("e@mail.com")
                .password("password")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        User user = User.builder()
                .name("userName")
                .email("e@mail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(jwtManager.generateAccessToken(user)).thenReturn("generatedAccessToken");
        when(jwtManager.generateRefreshToken(user)).thenReturn("generatedRefreshToken");

        AuthEntity returnedAuthEntity = authenticationService.register(authEntity);

        verify(userService).create(user);
        verify(jwtManager).generateAccessToken(user);
        verify(jwtManager).generateRefreshToken(user);
        assertEquals(AuthEntity.builder()
                .accessToken("generatedAccessToken")
                .refreshToken("generatedRefreshToken")
                .build(), returnedAuthEntity);
    }

    @Test
    public void authenticateFailedAuthentication() {
        AuthEntity authEntity = AuthEntity.builder()
                .name("userName")
                .email("e@mail.com")
                .password("password")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        UsernamePasswordAuthenticationToken userPassAuthToken = new UsernamePasswordAuthenticationToken("e@mail.com", "password");
        when(authenticationManager.authenticate(userPassAuthToken)).thenThrow(CredentialsExpiredException.class);

        assertThrows(AuthenticationException.class, () -> authenticationService.authenticate(authEntity));

        verifyNoInteractions(userService);
        verifyNoInteractions(jwtManager);
    }

    @Test
    public void authenticateAbsentUser() {
        AuthEntity authEntity = AuthEntity.builder()
                .name("userName")
                .email("e@mail.com")
                .password("password")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        when(userService.getByEmail("e@mail.com")).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> authenticationService.authenticate(authEntity));

        verify(userService).getByEmail("e@mail.com");
        verifyNoInteractions(jwtManager);
    }

    @Test
    public void authenticateSuccessfully() {
        AuthEntity authEntity = AuthEntity.builder()
                .name("userName")
                .email("e@mail.com")
                .password("password")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        User user = User.builder()
                .id(1L)
                .name("userName")
                .email("e@mail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
        when(userService.getByEmail("e@mail.com")).thenReturn(user);
        when(jwtManager.generateAccessToken(user)).thenReturn("generatedAccessToken");
        when(jwtManager.generateRefreshToken(user)).thenReturn("generatedRefreshToken");

        AuthEntity returnedAuthEntity = authenticationService.authenticate(authEntity);

        verify(userService).getByEmail("e@mail.com");
        verify(jwtManager).generateAccessToken(user);
        verify(jwtManager).generateRefreshToken(user);
        assertEquals(AuthEntity.builder()
                .accessToken("generatedAccessToken")
                .refreshToken("generatedRefreshToken")
                .build(), returnedAuthEntity);
    }

    @Test
    public void refreshWithInvalidToken() {
        AuthEntity authEntity = AuthEntity.builder()
                .name("userName")
                .email("e@mail.com")
                .password("password")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        when(jwtManager.isValidRefreshToken("refreshToken")).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> authenticationService.refresh(authEntity));

        verifyNoMoreInteractions(jwtManager);
        verifyNoInteractions(userService);
    }

    @Test
    public void refreshAbsentUser() {
        AuthEntity authEntity = AuthEntity.builder()
                .name("userName")
                .email("e@mail.com")
                .password("password")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        when(jwtManager.isValidRefreshToken("refreshToken")).thenReturn(true);
        when(jwtManager.extractRefreshSubject("refreshToken")).thenReturn("token@mail.com");
        when(userService.getByEmail("token@mail.com")).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> authenticationService.refresh(authEntity));

        verifyNoMoreInteractions(jwtManager);
        verifyNoMoreInteractions(userService);
    }

    @Test
    public void refreshSuccessfully() {
        AuthEntity authEntity = AuthEntity.builder()
                .name("userName")
                .email("e@mail.com")
                .password("password")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();
        User user = User.builder()
                .id(1L)
                .name("userName")
                .email("token@mail.com")
                .password("encodedPassword")
                .role(Role.USER)
                .build();
        when(jwtManager.isValidRefreshToken("refreshToken")).thenReturn(true);
        when(jwtManager.extractRefreshSubject("refreshToken")).thenReturn("token@mail.com");
        when(userService.getByEmail("token@mail.com")).thenReturn(user);
        when(jwtManager.generateAccessToken(user)).thenReturn("generatedAccessToken");
        when(jwtManager.generateRefreshToken(user)).thenReturn("generatedRefreshToken");

        AuthEntity returnedAuthEntity = authenticationService.refresh(authEntity);

        verify(userService).getByEmail("token@mail.com");
        verify(jwtManager).generateAccessToken(user);
        verify(jwtManager).generateRefreshToken(user);
        assertEquals(AuthEntity.builder()
                .accessToken("generatedAccessToken")
                .refreshToken("generatedRefreshToken")
                .build(), returnedAuthEntity);
    }
}