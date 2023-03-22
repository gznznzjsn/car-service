package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.user.AuthEntity;
import com.gznznzjsn.carservice.service.AuthenticationService;
import com.gznznzjsn.carservice.web.dto.AuthEntityDto;
import com.gznznzjsn.carservice.web.dto.group.OnAuthenticate;
import com.gznznzjsn.carservice.web.dto.group.OnRefresh;
import com.gznznzjsn.carservice.web.dto.group.OnRegister;
import com.gznznzjsn.carservice.web.dto.mapper.AuthEntityMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    @Operation(
            tags = {"Authentication"},
            summary = "Register new user",
            description = "Enter user's name, email and password to register him and obtain access token and refresh token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully registered",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthEntityDto.class))
                    )
            }
    )
    public AuthEntityDto register(@Validated(OnRegister.class) @RequestBody AuthEntityDto authEntityDto) {
        AuthEntity authEntity = authEntityMapper.toEntity(authEntityDto);
        return authEntityMapper.toDto(
                authenticationService.register(authEntity)
        );
    }

    @PostMapping("/authenticate")
    @Operation(
            tags = {"Authentication"},
            summary = "Authenticate existing user",
            description = "Enter user's email and password to obtain access token and refresh token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User successfully authenticated",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthEntityDto.class))
                    )
            }
    )
    public AuthEntityDto authenticate(@Validated(OnAuthenticate.class) @RequestBody AuthEntityDto authEntityDto) {
        AuthEntity authEntity = authEntityMapper.toEntity(authEntityDto);
        return authEntityMapper.toDto(
                authenticationService.authenticate(authEntity)
        );
    }

    @PostMapping("/refresh")
    @Operation(
            tags = {"Authentication"},
            summary = "Refresh tokens",
            description = "Enter unexpired refresh token to obtain new access and refresh tokens",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tokens were successfully refreshed",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthEntityDto.class))
                    )
            }
    )
    public AuthEntityDto refresh(@Validated(OnRefresh.class) @RequestBody AuthEntityDto authEntityDto) {
        AuthEntity authEntity = authEntityMapper.toEntity(authEntityDto);
        return authEntityMapper.toDto(
                authenticationService.refresh(authEntity)
        );
    }

}
