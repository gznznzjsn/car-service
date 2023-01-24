package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.TaskDao;
import com.gznznzjsn.carservice.domain.Task;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    @Override
    @Transactional(readOnly = true)
    public Task get(Long taskId) {
        return taskDao.findById(taskId)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id=" + taskId + " not found!"));

    }

    @Override
    public List<Task> getTasks(Long assignmentId) {
        return taskDao.findAllByAssignmentId(assignmentId);
    }

}
