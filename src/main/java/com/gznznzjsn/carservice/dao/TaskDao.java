package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.Task;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;
@Mapper
public interface TaskDao {

    Optional<Task> findById(Long taskId);

    List<Task> findAllByAssignmentId(Long assignmentId);

}
