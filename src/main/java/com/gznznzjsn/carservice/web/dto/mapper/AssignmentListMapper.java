package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class})
public interface AssignmentListMapper {

    List<Assignment> toEntity(List<AssignmentDto> dto);

    List<AssignmentDto> toDto(List<Assignment> sentAssignments);

}
