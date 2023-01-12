package com.gznznzjsn.carservice.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public enum UserDto {
    ;

    public enum Response {
        ;

        public record Read(
                @NotNull(message = "Id is mandatory!")
                Long id,

                @NotBlank(message = "User's name can't be blank!")
                @Length(max = 40, message = "Too long name!")
                String name) {
        }
    }
}
