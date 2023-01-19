package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;
@Mapper
public interface TaskDao {

    Optional<Task> read(Long taskId);


    List<Task> readTasks(Long assignmentId);
}
