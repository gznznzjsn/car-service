package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.AssignmentDao;
import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.domain.carservice.enums.AssignmentStatus;
import com.gznznzjsn.carservice.domain.exception.UnsuitableResourceException;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.PeriodService;
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
    private final PeriodService periodService;

    @Override
    @Transactional
    public Assignment createAssignment(Long orderId, LocalDateTime arrivalTime, Assignment assignment) {
        assignment.setStatus(AssignmentStatus.UNDER_CONSIDERATION);
        System.out.println(assignment);
        Assignment createdAssignment = assignmentDao.createAssignment(orderId, arrivalTime, assignment);
        List<Task> returnedTasks = new ArrayList<>();
        assignment.getTasks().forEach(t -> returnedTasks.add(taskService.readTask(t.getId())));
        createdAssignment.setTasks(returnedTasks);
        int totalDuration = createdAssignment.getTasks().stream()
                .map(Task::getDuration)
                .reduce(0, Integer::sum);
        Period appropriatePeriod = periodService.eraseAppropriatePeriod(arrivalTime, assignment.getSpecialization(), totalDuration);
        createdAssignment.setEmployee(appropriatePeriod.getEmployee());
        LocalDateTime startTime = appropriatePeriod.getDate().atTime(appropriatePeriod.getStart(), 0);
        createdAssignment.setStartTime(startTime);
        createdAssignment = assignmentDao.updateAssignment(createdAssignment);
        return createdAssignment;
    }

    @Override
    @Transactional
    public Assignment acceptAssignment(Assignment assignment) {

        assignment.setStatus(AssignmentStatus.ACCEPTED);
        Assignment updatedAssignment = assignmentDao.acceptAssignment(assignment);

//        if (!assignment.getSpecialization().equals(assignment.getEmployee().getSpecialization())) {
//            throw new UnsuitableResourceException("Assignment with specialization " + assignment.getSpecialization() + " cannot be accepted by employee with specialization " + assignment.getEmployee().getSpecialization());
//        }
        return updatedAssignment;
    }


}
