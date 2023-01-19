package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.web.dto.group.OnCreateOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserDto(

        @NotNull(message = "Id is mandatory!",groups = {OnCreateOrder.class})
        Long id,

        @NotBlank(message = "User's name can't be blank!")
        @Length(max = 40, message = "Too long name!")
        String name

) {
}

