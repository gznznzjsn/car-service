package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.Task;

import java.util.List;

public interface TaskService {

    Task get(Long id);

    List<Task> getTasks(Long assignmentId);

}
