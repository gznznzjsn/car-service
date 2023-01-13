package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.web.dto.group.OnCreateAssignment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

public record TaskDto(

        @NotNull(message = "Task id is mandatory!", groups = {OnCreateAssignment.class})
        Long id,

        @NotBlank(message = "Task name can't be blank!")
        @Length(max = 40, message = "Too long name!")
        String name,

        @Positive(message = "Task duration must be positive!")
        int duration,

        @NotNull(message = "Cost per hour must be set!")
        @Positive(message = "Cost per hour must be positive!")
        BigDecimal costPerHour

) {
}