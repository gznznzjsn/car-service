package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;


public enum EmployeeDto {
    ;

    public enum Response {
        ;

        public record Create(Long id, String name, Specialization specialization) {
        }
    }
}

