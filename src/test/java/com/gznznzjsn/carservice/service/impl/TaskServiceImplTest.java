package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.TaskDao;
import com.gznznzjsn.carservice.domain.Specialization;
import com.gznznzjsn.carservice.domain.Task;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    TaskDao taskDao;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void getNonExisting() {
        when(taskDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.get(1L));

        verify(taskDao).findById(1L);
    }

    @Test
    void getExisting() {
        Task task = Task.builder()
                .id(1L)
                .name("taskName")
                .duration(3)
                .specialization(Specialization.CLEANER)
                .costPerHour(BigDecimal.valueOf(99))
                .build();
        when(taskDao.findById(1L)).thenReturn(Optional.of(task));

        Task returnedTask = taskService.get(1L);

        verify(taskDao).findById(1L);
        assertEquals(task, returnedTask);
    }

    @Test
    void getAllByOrderIdAtLeastOne() {
        Task task1 = Task.builder().id(4L)
                .name("taskName4")
                .duration(4)
                .specialization(Specialization.CLEANER)
                .costPerHour(BigDecimal.valueOf(994)).build();
        Task task2 = Task.builder()
                .id(5L)
                .name("taskName5")
                .duration(5)
                .specialization(Specialization.REPAIRER)
                .costPerHour(BigDecimal.valueOf(995)).build();
        when(taskDao.findAllByAssignmentId(1L)).thenReturn(List.of(task1, task2));

        List<Task> taskList = taskService.getTasks(1L);

        verify(taskDao).findAllByAssignmentId(1L);
        assertEquals(task1, taskList.get(0));
        assertEquals(task2, taskList.get(1));
        assertEquals(2, taskList.size());
    }

    @Test
    void getAllByOrderIdNone() {
        when(taskDao.findAllByAssignmentId(1L)).thenReturn(new ArrayList<>());

        List<Task> taskList = taskService.getTasks(1L);

        verify(taskDao).findAllByAssignmentId(1L);
        assertEquals(0, taskList.size());
    }
}