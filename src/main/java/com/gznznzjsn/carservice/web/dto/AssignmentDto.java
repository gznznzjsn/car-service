package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;

import java.util.List;

public enum AssignmentDto {
    ;

    public enum Request {
        ;

        public record Create(Specialization specialization, List<TaskDto.Request.AddToAssignment> requestedTasks, String commentary) {
        }

    }


}
