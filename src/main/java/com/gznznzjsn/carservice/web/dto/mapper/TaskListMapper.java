package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.web.dto.TaskDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring", uses = {TaskMapper.class})
@Component
public interface TaskListMapper {

    List<TaskDto.Response.Read> toReadDto(List<Task> entity);

    List<Task> toEntity(List<TaskDto.Request.AddToAssignment> dto);

}
