package com.gznznzjsn.carservice.web.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public enum OrderDto {
    ;

    public enum Request {
        ;

        public record Create(LocalDateTime arrivalTime, List<AssignmentDto.Request.Create> requestedAssignments) {
        }
    }

    public enum Response {
        ;

        public record ReadPrecalculated(BigDecimal precalculatedTotalCost,
                                        List<AssignmentDto.Response.ReadPrecalculated> precalculatedAssignments,
                                        LocalDate precalculatedFinishTime) {
        }
    }
}
