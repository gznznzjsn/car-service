package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.TaskDao;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskDao taskDao;

    @Override
    @Transactional(readOnly = true)
    public Task readTask(Long id) {
        Optional<Task> optionalTask = taskDao.readTask(id);
        if (optionalTask.isEmpty()) {
            throw new ResourceNotFoundException("Task with id=" + id + " not found!");
        }
        return optionalTask.get();
    }
}
