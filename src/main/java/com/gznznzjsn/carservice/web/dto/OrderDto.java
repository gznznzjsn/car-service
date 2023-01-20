package com.gznznzjsn.carservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gznznzjsn.carservice.domain.order.OrderStatus;
import com.gznznzjsn.carservice.web.dto.group.OnCreateAssignment;
import com.gznznzjsn.carservice.web.dto.group.OnCreateOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDto(

        @NotNull(message = "Order id is mandatory!", groups = {OnCreateAssignment.class})
        Long id,

        @NotNull(message = "User must be set!", groups = {OnCreateOrder.class})
        @Valid
        UserDto user,

        @NotNull(message = "Arrival time must be set!", groups = {OnCreateOrder.class})
        LocalDateTime arrivalTime,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        OrderStatus status,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime createdAt,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime finishedAt,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        BigDecimal totalCost,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime finishTime

) {
}
