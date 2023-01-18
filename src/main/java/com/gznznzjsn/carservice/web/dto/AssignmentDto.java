package com.gznznzjsn.carservice.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gznznzjsn.carservice.domain.carservice.Specialization;
import com.gznznzjsn.carservice.domain.carservice.assignment.AssignmentStatus;
import com.gznznzjsn.carservice.web.dto.group.OnAccept;
import com.gznznzjsn.carservice.web.dto.group.OnCreateAssignment;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record AssignmentDto(

        @NotNull(message = "Id is mandatory!", groups = {OnAccept.class})
        Long id,

        @NotNull(message = "Order is mandatory for assignment!", groups = {OnCreateAssignment.class})
        @Valid
        OrderDto order,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        AssignmentStatus status,

        @NotNull(message = "Specialization is mandatory!", groups = {OnCreateAssignment.class})
        Specialization specialization,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        LocalDateTime startTime,

        @NotNull(message = "You need to set final cost!", groups = {OnAccept.class})
        @Positive(message = "Final cost must be positive!", groups = {OnAccept.class})
        BigDecimal finalCost,

        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        EmployeeDto employee,

        @NotNull(message = "You need to add at least one task!", groups = {OnCreateAssignment.class})
        @Valid
        List<TaskDto> tasks,

        @Length(max = 255, message = "Too long commentary!")
        String userCommentary,

        @Length(max = 255, message = "Too long commentary!", groups = {OnAccept.class})
        String employeeCommentary

) {
}
