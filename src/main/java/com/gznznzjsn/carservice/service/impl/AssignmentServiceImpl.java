package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.AssignmentDao;
import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.EmployeeService;
import com.gznznzjsn.carservice.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentDao assignmentDao;
    private final TaskService taskService;

    private final EmployeeService employeeService;

    @Override
    @Transactional
    public Assignment createAssignment(Long orderId, LocalDateTime arrivalTime, Assignment assignment) {
        Assignment createdAssignment = assignmentDao.createAssignment(orderId, arrivalTime, assignment);
        List<Task> returnedTasks = new ArrayList<>();
        assignment.getTasks().forEach(t -> returnedTasks.add(taskService.readTask(t.getId())));
        createdAssignment.setTasks(returnedTasks);
//        employeeService.getAppropriateEmployee(arrivalTime, totalDuration, assignment.getSpecialization());
        return createdAssignment;
    }
}
