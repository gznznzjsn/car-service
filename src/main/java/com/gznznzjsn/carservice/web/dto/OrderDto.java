package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.carservice.order.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public enum OrderDto {
    ;

    public enum Request {
        ;

        public record Create(LocalDateTime arrivalTime) {
        }
    }

    public enum Response {
        ;

        public record ReadPrecalculated(
                @Positive(message = "Total cost must be positive!")
                BigDecimal precalculatedTotalCost,

                @NotNull(message = "Order must have at least one assignment!")
                List<AssignmentDto.Response.ReadPrecalculated> precalculatedAssignments,
                LocalDateTime finishTime) {
        }

        public record Create(
                @NotNull(message = "Id is mandatory!")
                Long id,

                @NotNull(message = "User must be set!")
                @Valid
                UserDto.Response.Read user,

                @NotNull(message = "Status must be set!")
                OrderStatus status,

                @NotNull(message = "Arrival time must be set!")
                LocalDateTime arrivalTime) {
        }

        public record Read(
                @NotNull(message = "Id is mandatory!")
                Long id,

                @NotNull(message = "User must be set!")
                @Valid
                UserDto.Response.Read user,

                @NotNull(message = "Status must be set!")
                OrderStatus status,

                @NotNull(message = "Arrival time must be set!")
                LocalDateTime arrivalTime,

                @NotNull(message = "CreatedAt column can't be empty!")
                LocalDateTime createdAt,

                LocalDateTime finishedAt) {
        }
    }
}
