package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, TaskListMapper.class})
@Component
public interface AssignmentMapper {

    Assignment toEntity(AssignmentDto.Request.Create dto);

    Assignment toEntity(AssignmentDto.Request.Accept dto);


    AssignmentDto.Response.Create toCreateDto(Assignment createdAssignment);

    AssignmentDto.Response.Read toReadDto(Assignment assignment);

    @Mapping(target = "precalculatedFinalCost", expression = "java(com.gznznzjsn.carservice.service.AssignmentService.calculateTotalCost(entity))")
    AssignmentDto.Response.Sent toSentDto(Assignment entity);

}
