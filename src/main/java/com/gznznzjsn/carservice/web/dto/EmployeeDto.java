package com.gznznzjsn.carservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gznznzjsn.carservice.domain.Specialization;
import com.gznznzjsn.carservice.web.dto.group.OnCreateEmployee;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


public record EmployeeDto(

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Long id,

        @NotBlank(message = "Employee's name can't be blank!", groups = {OnCreateEmployee.class})
        @Length(max = 40, message = "Too long name!", groups = {OnCreateEmployee.class})
        @Schema(example = "employee_name")
        String name,

        @NotNull(message = "Specialization must be set!", groups = {OnCreateEmployee.class})
        @Schema(example = "CLEANER")
        Specialization specialization

) {
}
