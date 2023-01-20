package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.AuthEntity;
import com.gznznzjsn.carservice.service.AuthenticationService;
import com.gznznzjsn.carservice.web.dto.AuthEntityDto;
import com.gznznzjsn.carservice.web.dto.group.OnAuthenticate;
import com.gznznzjsn.carservice.web.dto.group.OnRegister;
import com.gznznzjsn.carservice.web.dto.mapper.AuthEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final AuthEntityMapper authEntityMapper;

    @PostMapping("/register")
    public AuthEntityDto register(@Validated(OnRegister.class) @RequestBody AuthEntityDto authEntityDto) {
        AuthEntity authEntity = authEntityMapper.toEntity(authEntityDto);
        return authEntityMapper.toDto(authenticationService.register(authEntity));
    }

    @PostMapping("/authenticate")
    public AuthEntityDto authenticate(@Validated(OnAuthenticate.class) @RequestBody AuthEntityDto authEntityDto) {
        AuthEntity authEntity = authEntityMapper.toEntity(authEntityDto);
        return authEntityMapper.toDto(authenticationService.authenticate(authEntity));
    }

}
