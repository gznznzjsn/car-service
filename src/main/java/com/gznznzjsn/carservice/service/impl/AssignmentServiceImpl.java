package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.AssignmentDao;
import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.carservice.Task;
import com.gznznzjsn.carservice.domain.carservice.assignment.AssignmentStatus;
import com.gznznzjsn.carservice.domain.carservice.order.OrderStatus;
import com.gznznzjsn.carservice.domain.exception.IllegalActionException;
import com.gznznzjsn.carservice.domain.exception.NotEnoughResourcesException;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.service.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentDao assignmentDao;
    private final OrderService orderService;
    private final PeriodService periodService;


    @Override
    @Transactional
    public Assignment createAssignment(Assignment assignment) {
        if (!assignment.getOrder().getStatus().equals(OrderStatus.NOT_SENT)) {
            throw new IllegalActionException("You can't add assignment to already sent order!");
        }
        if (assignment.getTasks().isEmpty()) {
            throw new NotEnoughResourcesException("You can't create assignment without tasks!");
        }
        assignment.setStatus(AssignmentStatus.NOT_SENT);
        assignmentDao.createAssignment(assignment);
        return assignment;
    }

    @Override
    @Transactional
    public List<Assignment> sendAssignmentsAndOrder(Long orderId) {
        orderService.sendOrder(orderId);
        List<Assignment> assignments = getAssignments(orderId);
        if (assignments.isEmpty()) {
            throw new NotEnoughResourcesException("You cannot send order without assignments!");
        }
        List<Assignment> updatedAssignments = new ArrayList<>();
        assignments.forEach(a -> {
            if (!a.getStatus().equals(AssignmentStatus.NOT_SENT)) {
                throw new IllegalActionException("You can't send assignment with id = " + a.getId() + ", because it's already sent!");
            }
            System.out.println(a);
//            int totalDuration = 3;
            int totalDuration = a.getTasks().stream()
                    .map(Task::getDuration)
                    .reduce(0, Integer::sum);
            Period appropriatePeriod = periodService.eraseAppropriatePeriod(a.getOrder().getArrivalTime(), a.getSpecialization(), totalDuration);
            Assignment assignmentToUpdate = Assignment.builder()
                    .id(a.getId())
                    .status(AssignmentStatus.UNDER_CONSIDERATION)
                    .employee(
                            appropriatePeriod.getEmployee()
                    )
                    .startTime(appropriatePeriod.getDate().atTime(appropriatePeriod.getStart(), 0))
                    .build();
            updatedAssignments.add(
                    updateAssignment(assignmentToUpdate)
            );
        });

        return updatedAssignments;
    }

    @Override
    @Transactional
    public Assignment updateAssignment(Assignment assignment) {
        Assignment assignmentFromRepository = getAssignment(assignment.getId());
        if (assignment.getStatus() != null) {
            assignmentFromRepository.setStatus(assignment.getStatus());
        }
        if (assignment.getStartTime() != null) {
            assignmentFromRepository.setStartTime(assignment.getStartTime());
        }
        if (assignment.getFinalCost() != null) {
            assignmentFromRepository.setFinalCost(assignment.getFinalCost());
        }
        if (assignment.getEmployee() != null) {
            assignmentFromRepository.setEmployee(assignment.getEmployee());
        }
        if (assignment.getUserCommentary() != null) {
            assignmentFromRepository.setUserCommentary(assignment.getUserCommentary());
        }
        if (assignment.getEmployeeCommentary() != null) {
            assignmentFromRepository.setEmployeeCommentary(assignment.getEmployeeCommentary());
        }
        if (assignment.getTasks() != null) {
            assignmentFromRepository.setTasks(assignment.getTasks());
        }
        assignmentDao.updateAssignment(assignmentFromRepository);

        return assignmentFromRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Assignment getAssignment(Long assignmentId) {
        return assignmentDao.readAssignment(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment with id = " + assignmentId + " doesn't exist!"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Assignment> getAssignments(Long orderId) {
        return assignmentDao.readAssignments(orderId);
    }


}
