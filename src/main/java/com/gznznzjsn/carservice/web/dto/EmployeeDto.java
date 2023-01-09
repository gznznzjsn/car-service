package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;


public enum EmployeeDto {
    ;

    public enum Request {
        ;

        public record Create(String name, Specialization specialization) {
        }
    }

    public enum Response {
        ;

        public record Read(Long id, String name, Specialization specialization) {
        }

        public record ReadWithoutSpecialization(Long id, String name) {
        }
    }
}

