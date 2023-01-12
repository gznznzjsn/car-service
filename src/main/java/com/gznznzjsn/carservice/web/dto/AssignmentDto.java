package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.domain.carservice.order.Order;
import com.gznznzjsn.carservice.domain.carservice.assignment.AssignmentStatus;
import com.gznznzjsn.carservice.domain.carservice.Specialization;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public enum AssignmentDto {
    ;

    public enum Request {
        ;

        public record Create(
                @NotNull(message = "Specialization is mandatory!")
                @Valid
                OrderDto.Request.AddToAssignment order,

                @NotNull(message = "Specialization is mandatory!")
                Specialization specialization,

                @NotNull(message = "You need to add at least one task!")
                List<TaskDto.Request.AddToAssignment> tasks,

                @Length(max = 255, message = "Too long commentary!")
                String userCommentary
        ) {
        }

        public record Accept(

                @Length(max = 255, message = "Too long commentary!")
                String employeeCommentary,

                @NotNull(message = "You need to set final cost!")
                @Positive(message = "Final cost must be positive!")
                BigDecimal finalCost) {
        }

    }

    public enum Response {
        ;

        public record ReadPrecalculated(Specialization specialization,
                                        EmployeeDto.Response.Read employee,
                                        List<TaskDto.Response.Read> tasks, BigDecimal precalculatedCost) {
        }

        public record Read(

                @NotNull(message = "Id is mandatory!")
                Long id,

                @NotNull(message = "Order is mandatory for assignment!")
                @Valid
                OrderDto.Response.Read order,

                @NotNull(message = "Status must be set!")
                AssignmentStatus status,

                @NotNull(message = "Specialization must be set!")
                Specialization specialization,

                LocalDateTime startTime,

                BigDecimal finalCost,

                @Valid
                EmployeeDto.Response.Read employee,

                @NotNull(message = "You need to add at least one task!")
                List<TaskDto.Response.Read> tasks,

                @Length(max = 255, message = "Too long commentary!")
                String userCommentary,

                @Length(max = 255, message = "Too long commentary!")
                String employeeCommentary
        ) {
        }

        public record Create(

                @NotNull(message = "Id is mandatory!")
                Long id,

                @NotNull(message = "Order is mandatory for assignment!")
                OrderDto.Response.Read order,

                @NotNull(message = "Status must be set!")
                AssignmentStatus status,

                @NotNull(message = "Specialization must be set!")
                Specialization specialization,

                @NotNull(message = "You need to add at least one task!")
                List<TaskDto.Response.Read> tasks,

                @Length(max = 255, message = "Too long commentary!")
                String userCommentary
        ) {
        }

        public record Sent(

                @NotNull(message = "Id is mandatory!")
                Long id,

                @NotNull(message = "Order is mandatory for assignment!")
                @Valid
                Order order,

                @NotNull(message = "Status must be set!")
                AssignmentStatus status,

                @NotNull(message = "Specialization must be set!")
                Specialization specialization,

                LocalDateTime startTime,

                @Positive(message = "Final cost must be positive!")
                BigDecimal precalculatedFinalCost,

                @NotNull(message = "Employee must be found!")
                @Valid
                Employee employee,

                @NotNull(message = "You need to add at least one task!")
                List<TaskDto.Response.Read> tasks,

                @Length(max = 255, message = "Too long commentary!")
                String userCommentary
        ) {
        }
    }


}
