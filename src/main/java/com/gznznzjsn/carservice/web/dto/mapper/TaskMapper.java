package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.web.dto.TaskDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface TaskMapper {
    Task toEntity(TaskDto.Request.AddToAssignment dto);
    TaskDto.Response.Read toReadDto(Task entity);
}
