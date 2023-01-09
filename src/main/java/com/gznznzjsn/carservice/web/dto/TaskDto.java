package com.gznznzjsn.carservice.web.dto;

import java.math.BigDecimal;

public enum TaskDto {
    ;

    public enum Request {
        ;

        public record AddToAssignment(Long id) {
        }
    }

    public enum Response{
        ;
        public record Read(String name, int duration, BigDecimal costPerHour){}
    }
}
