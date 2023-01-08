package com.gznznzjsn.carservice.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

public enum OrderDto {
    ;
    public enum Request {
        ;
        public record Create(LocalDateTime arrivalTime, List<AssignmentDto.Request.Create> requestedAssignments){}
    }
}
