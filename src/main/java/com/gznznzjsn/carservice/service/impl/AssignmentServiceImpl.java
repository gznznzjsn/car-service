package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.AssignmentDao;
import com.gznznzjsn.carservice.domain.Period;
import com.gznznzjsn.carservice.domain.Specialization;
import com.gznznzjsn.carservice.domain.Task;
import com.gznznzjsn.carservice.domain.assignment.Assignment;
import com.gznznzjsn.carservice.domain.assignment.AssignmentStatus;
import com.gznznzjsn.carservice.domain.exception.IllegalActionException;
import com.gznznzjsn.carservice.domain.exception.NotEnoughResourcesException;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.domain.order.Order;
import com.gznznzjsn.carservice.domain.order.OrderStatus;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.service.PeriodService;
import com.gznznzjsn.carservice.service.TaskService;
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
    private final TaskService taskService;

    @Override
    @Transactional
    public Assignment create(Assignment assignment) {
        Assignment assignmentToCreate = Assignment.builder()
                .id(assignment.getId())
                .status(AssignmentStatus.NOT_SENT)
                .order(assignment.getOrder())
                .tasks(assignment.getTasks())
                .userCommentary(assignment.getUserCommentary())
                .build();
        Order order = orderService.get(assignmentToCreate.getOrder().getId());
        assignmentToCreate.setOrder(order);
        if (!OrderStatus.NOT_SENT.equals(assignmentToCreate.getOrder().getStatus())) {
            throw new IllegalActionException("You can't add assignment to already sent order!");
        }
        List<Task> tasks = assignmentToCreate.getTasks();
        if (tasks == null || tasks.isEmpty()) {
            throw new NotEnoughResourcesException("You can't create assignment without tasks!");
        }
        Specialization probableSpecialization = taskService.get(tasks.get(0).getId()).getSpecialization();
        for (Task task : tasks) {
            if (!taskService.get(task.getId()).getSpecialization().equals(probableSpecialization)) {
                throw new IllegalActionException("You can't create assignment with multiple specializations!");
            }
        }
        assignmentToCreate.setSpecialization(probableSpecialization);
        assignmentDao.create(assignmentToCreate);
        assignmentDao.createTasks(assignmentToCreate);
        assignmentToCreate.setTasks(taskService.getTasks(assignmentToCreate.getId()));
        return assignmentToCreate;
    }

    @Override
    @Transactional
    public List<Assignment> sendWithOrder(Long orderId) {
        orderService.send(orderId);
        List<Assignment> assignments = getAllByOrderId(orderId);
        if (assignments == null || assignments.isEmpty()) {
            throw new NotEnoughResourcesException("You cannot send order without assignments!");
        }
        List<Assignment> updatedAssignments = new ArrayList<>();
        assignments.forEach(a -> {
            if (!AssignmentStatus.NOT_SENT.equals(a.getStatus())) {
                throw new IllegalActionException("You can't send assignment with id = " + a.getId() + ", because it's already sent!");
            }
            int totalDuration = a.getTasks().stream()
                    .map(Task::getDuration)
                    .reduce(0, Integer::sum);
            Period appropriatePeriod = periodService.eraseAppropriate(a.getOrder().getArrivalTime(), a.getSpecialization(), totalDuration);
            Assignment assignmentToUpdate = Assignment.builder()
                    .id(a.getId())
                    .status(AssignmentStatus.UNDER_CONSIDERATION)
                    .employee(
                            appropriatePeriod.getEmployee()
                    )
                    .startTime(appropriatePeriod.getDate().atTime(appropriatePeriod.getStart(), 0))
                    .build();
            updatedAssignments.add(
                    update(assignmentToUpdate)
            );
        });
        return updatedAssignments;
    }

    @Override
    @Transactional
    public Assignment update(Assignment assignment) {
        Assignment assignmentFromRepository = get(assignment.getId());
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
        assignmentDao.update(assignmentFromRepository);

        return assignmentFromRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Assignment get(Long assignmentId) {
        Assignment assignment = assignmentDao.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment with id = " + assignmentId + " doesn't exist!"));
        assignment.setTasks(taskService.getTasks(assignment.getId()));
        return assignment;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Assignment> getAllByOrderId(Long orderId) {
        List<Assignment> assignments = assignmentDao.findAllByOrderId(orderId);
        assignments.forEach(a -> a.setTasks(taskService.getTasks(a.getId())));
        return assignments;
    }

    @Override
    @Transactional
    public Assignment accept(Assignment assignment) {
        Assignment existingAssignment = get(assignment.getId());
        if (!AssignmentStatus.UNDER_CONSIDERATION.equals(existingAssignment.getStatus())) {
            throw new IllegalActionException("Assignment with id = " + existingAssignment.getId() + " is not under consideration!");
        }
        Assignment assignmentToUpdate = Assignment.builder()
                .id(assignment.getId())
                .status(AssignmentStatus.ACCEPTED)
                .finalCost(assignment.getFinalCost())
                .employeeCommentary(assignment.getEmployeeCommentary())
                .build();
        return update(assignmentToUpdate);
    }

}
