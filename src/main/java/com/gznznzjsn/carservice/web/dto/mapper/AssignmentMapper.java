package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface AssignmentMapper {
    @Mapping(target = "tasks", source = "dto.requestedTasks")
    Assignment toEntity(AssignmentDto.Request.Create dto);
}
