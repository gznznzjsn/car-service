package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AssignmentMapper {
    @Mapping(target = "tasks", source = "dto.requestedTasks")
    Assignment toEntity(AssignmentDto.Request.Create dto);

    Assignment toEntity(AssignmentDto.Request.Accept dto);

    @Mapping(target = "precalculatedCost",expression = "java(com.gznznzjsn.carservice.service.AssignmentService.calculateTotalCost(entity))")
    AssignmentDto.Response.ReadPrecalculated toDto(Assignment entity);
}
