package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.domain.carservice.enums.AssignmentStatus;
import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;

import java.math.BigDecimal;
import java.util.List;

public enum AssignmentDto {
    ;

    public enum Request {
        ;

        public record Create(Specialization specialization, List<TaskDto.Request.AddToAssignment> requestedTasks,
                             String userCommentary) {
        }

        public record Accept(String employeeCommentary, BigDecimal finalCost) {
        }

    }

    public enum Response {
        ;

        public record ReadPrecalculated(Specialization specialization,
                                        EmployeeDto.Response.ReadWithoutSpecialization employee,
                                        List<TaskDto.Response.Read> tasks, BigDecimal precalculatedCost) {
        }

        public record Read(Specialization specialization, EmployeeDto.Response.ReadWithoutSpecialization employee,
                           List<TaskDto.Response.Read> tasks, BigDecimal finalCost) {
        }
    }


}
