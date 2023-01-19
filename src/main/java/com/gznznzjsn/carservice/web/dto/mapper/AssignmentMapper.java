package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, TaskListMapper.class})
public interface AssignmentMapper {

    Assignment toEntity(AssignmentDto dto);

    @Mapping(target = "finalCost", expression = "java(com.gznznzjsn.carservice.service.AssignmentService.calculateTotalCost(entity))")
    AssignmentDto toDto(Assignment entity);

}
