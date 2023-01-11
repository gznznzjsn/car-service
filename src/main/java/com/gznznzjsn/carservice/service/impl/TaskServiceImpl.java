package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.TaskDao;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    @Override
    @Transactional(readOnly = true)
    public Task readTask(Long id) {
        return taskDao.readTask(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with id=" + id + " not found!"));

    }
}
