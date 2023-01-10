package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Task;

import java.util.Optional;

public interface TaskDao {
    Optional<Task> readTask(Long id);
}
