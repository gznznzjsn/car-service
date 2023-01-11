package com.gznznzjsn.carservice.web.dto;

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

        public record ReadPrecalculated(BigDecimal precalculatedTotalCost,
                                        List<AssignmentDto.Response.ReadPrecalculated> precalculatedAssignments,
                                        LocalDateTime finishTime) {
        }
    }
}
