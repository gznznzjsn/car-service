package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class})
@Component
public interface AssignmentListMapper {

    List<Assignment> toEntity(List<AssignmentDto.Request.Create> dto);

    List<AssignmentDto.Response.Sent> toSentDto(List<Assignment> sentAssignments);
}
