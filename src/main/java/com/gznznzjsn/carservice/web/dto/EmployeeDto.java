package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.carservice.Specialization;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;


public enum EmployeeDto {
    ;

    public enum Request {
        ;

        public record Create(
                @NotBlank(message = "You can't create employee with an empty name!")
                @Length(max = 40, message = "Too long name!")

                String name,

                @NotNull(message = "Specialization must be set!")
                Specialization specialization) {
        }
    }

    public enum Response {
        ;

        public record Read(
                @NotNull(message = "Id is mandatory!")
                Long id,

                @NotBlank(message = "Employee's name can't be blank!")
                @Length(max = 40, message = "Too long name!")
                String name,

                @NotNull(message = "Specialization must be set!")
                Specialization specialization) {
        }
    }
}

