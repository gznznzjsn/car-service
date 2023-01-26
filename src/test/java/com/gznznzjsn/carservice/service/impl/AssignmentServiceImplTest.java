package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.AssignmentDao;
import com.gznznzjsn.carservice.domain.Employee;
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
import com.gznznzjsn.carservice.domain.user.Role;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.OrderService;
import com.gznznzjsn.carservice.service.PeriodService;
import com.gznznzjsn.carservice.service.TaskService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AssignmentServiceImplTest {

    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Mock
    private AssignmentDao assignmentDao;

    @Mock
    private OrderService orderService;

    @Mock
    private PeriodService periodService;

    @Mock
    private TaskService taskService;

    @Test
    public void createForNonExistingOrder() {
        Assignment givenAssignment = Assignment.builder().order(Order.builder()
                .id(1L)
                .build()).build();
        when(orderService.get(1L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> assignmentService.create(givenAssignment));

        verify(assignmentDao, never()).create(any());
        verify(assignmentDao, never()).createTasks(any());
    }

    @Test
    public void createForSentOrder() {
        Assignment givenAssignment = Assignment.builder().order(Order.builder()
                .id(1L)
                .build()).build();
        when(orderService.get(1L)).thenReturn(Order.builder().status(OrderStatus.UNDER_CONSIDERATION).build());

        assertThrows(IllegalActionException.class, () -> assignmentService.create(givenAssignment));

        verify(assignmentDao, never()).create(any());
        verify(assignmentDao, never()).createTasks(any());
    }

    @Test
    public void createWithNoTasks() {
        Assignment givenAssignment = Assignment.builder().order(Order.builder()
                .id(1L)
                .build()).build();
        when(orderService.get(1L)).thenReturn(Order.builder().status(OrderStatus.NOT_SENT).build());

        assertThrows(NotEnoughResourcesException.class, () -> assignmentService.create(givenAssignment));

        verify(assignmentDao, never()).create(any());
        verify(assignmentDao, never()).createTasks(any());
    }

    @Test
    public void createWithDifferentSpecializationsTasks() {
        Assignment givenAssignment = Assignment.builder().order(Order.builder()
                        .id(1L)
                        .build())
                .tasks(List.of(
                        Task.builder().id(1L).build(),
                        Task.builder().id(2L).build()
                ))
                .build();
        when(orderService.get(1L)).thenReturn(Order.builder().status(OrderStatus.NOT_SENT).build());
        when(taskService.get(1L)).thenReturn(Task.builder().specialization(Specialization.CLEANER).build());
        when(taskService.get(2L)).thenReturn(Task.builder().specialization(Specialization.REPAIRER).build());

        assertThrows(IllegalActionException.class, () -> assignmentService.create(givenAssignment));

        verify(assignmentDao, never()).create(any());
        verify(assignmentDao, never()).createTasks(any());
    }

    @Test
    public void createCorrect() {
        Assignment givenAssignment = Assignment.builder()
                .status(AssignmentStatus.ACCEPTED)
                .order(Order.builder().id(1L).build())
                .startTime(LocalDateTime.MAX)
                .finalCost(BigDecimal.valueOf(9.99))
                .employee(new Employee(2L, "employee_name", Specialization.CLEANER))
                .userCommentary("blah blah from user")
                .employeeCommentary("blah blah")
                .tasks(List.of(
                        Task.builder().id(1L).build(),
                        Task.builder().id(2L).build()
                ))
                .build();
        when(orderService.get(1L)).thenReturn(Order.builder().id(1L).status(OrderStatus.NOT_SENT).build());
        doAnswer(invocation -> {
            Assignment a = invocation.getArgument(0);
            a.setId(1L);
            return null;
        }).when(assignmentDao).create(any());
        when(taskService.get(1L)).thenReturn(Task.builder().specialization(Specialization.CLEANER).build());
        when(taskService.get(2L)).thenReturn(Task.builder().specialization(Specialization.CLEANER).build());
        when(taskService.getTasks(1L)).thenReturn(List.of(
                Task.builder().id(1L).name("name1").build(),
                Task.builder().id(2L).name("name2").build()
        ));

        Assignment returnedAssignment = assignmentService.create(givenAssignment);

        assertEquals(1L, returnedAssignment.getId());
        assertEquals(givenAssignment.getOrder().getId(), returnedAssignment.getOrder().getId());
        assertEquals(Specialization.CLEANER, returnedAssignment.getSpecialization());
        assertEquals(givenAssignment.getTasks().size(), returnedAssignment.getTasks().size());
        assertEquals(givenAssignment.getTasks().get(0).getId(), returnedAssignment.getTasks().get(0).getId());
        assertEquals(givenAssignment.getTasks().get(1).getId(), returnedAssignment.getTasks().get(1).getId());
        assertEquals(givenAssignment.getUserCommentary(), returnedAssignment.getUserCommentary());
        assertEquals(AssignmentStatus.NOT_SENT, returnedAssignment.getStatus());
        assertNull(returnedAssignment.getStartTime());
        assertNull(returnedAssignment.getFinalCost());
        assertNull(returnedAssignment.getEmployee());
        assertNull(returnedAssignment.getEmployeeCommentary());
    }

    @Test
    public void sendWithNonExistingOrder() {
        AssignmentService spyAS = spy(assignmentService);
        when(orderService.send(1L)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> spyAS.sendWithOrder(1L));

        verifyNoInteractions(periodService);
        verify(spyAS, never()).update(any());
    }

    @Test
    public void sendWithEmptyOrder() {
        AssignmentService spyAS = spy(assignmentService);

        assertThrows(NotEnoughResourcesException.class, () -> spyAS.sendWithOrder(1L));

        verifyNoInteractions(periodService);
        verify(spyAS, never()).update(any());
    }

    @Test
    public void sendWithSentOrder() {
        AssignmentService spyAS = spy(assignmentService);
        when(orderService.send(1L)).thenThrow(IllegalActionException.class);

        assertThrows(IllegalActionException.class, () -> spyAS.sendWithOrder(1L));

        verifyNoInteractions(periodService);
        verify(spyAS, never()).update(any());
    }

    @Test
    public void sendWithSentAssignment() {
        AssignmentService spyAS = spy(assignmentService);
        doReturn(List.of(
                Assignment.builder()
                        .status(AssignmentStatus.UNDER_CONSIDERATION)
                        .tasks(List.of(new Task()))
                        .build()
        )).when(assignmentDao).findAllByOrderId(1L);

        assertThrows(IllegalActionException.class, () -> assignmentService.sendWithOrder(1L));

        verifyNoInteractions(periodService);
        verify(spyAS, never()).update(any());
    }

    @Test
    public void sendWithNoTimeAssignment() {
        AssignmentService spyAS = spy(assignmentService);
        Assignment assignment = Assignment.builder()
                .order(new Order())
                .status(AssignmentStatus.NOT_SENT)
                .tasks(List.of(
                        Task.builder().duration(2).build(),
                        Task.builder().duration(4).build()
                ))
                .build();
        doReturn(List.of(
                assignment
        )).when(spyAS).getAllByOrderId(1L);
        when(periodService.eraseAppropriate(null, null, 2 + 4)).thenThrow(new ResourceNotFoundException(""));

        assertThrows(ResourceNotFoundException.class, () -> spyAS.sendWithOrder(1L));

        verify(spyAS, never()).update(any());
    }

    @Test
    public void sendWithCorrectAssignments() {
        AssignmentService spyAS = spy(assignmentService);
        Assignment assignment1 = Assignment.builder()
                .id(14L)
                .order(new Order())
                .status(AssignmentStatus.NOT_SENT)
                .tasks(List.of(
                        Task.builder().duration(2).build(),
                        Task.builder().duration(4).build()
                ))
                .build();
        Assignment assignment2 = Assignment.builder()
                .id(15L)
                .order(new Order())
                .status(AssignmentStatus.NOT_SENT)
                .tasks(List.of(
                        Task.builder().duration(3).build(),
                        Task.builder().duration(5).build()
                ))
                .build();
        LocalDate date1 = LocalDate.now();
        LocalDate date2 = LocalDate.now();
        Assignment updated1 = Assignment.builder()
                .id(assignment1.getId())
                .status(AssignmentStatus.UNDER_CONSIDERATION)
                .employee(
                        Employee.builder().id(333L).build()
                )
                .startTime(date1.atTime(1, 0))
                .build();
        Assignment updated2 = Assignment.builder()
                .id(assignment2.getId())
                .status(AssignmentStatus.UNDER_CONSIDERATION)
                .employee(
                        Employee.builder().id(337L).build()
                )
                .startTime(date2.atTime(2, 0))
                .build();
        doReturn(List.of(
                assignment1,
                assignment2
        )).when(spyAS).getAllByOrderId(1L);
        when(periodService.eraseAppropriate(null, null, 2 + 4))
                .thenReturn(Period.builder()
                        .start(1)
                        .employee(Employee.builder().id(333L).build())
                        .date(date1)
                        .build());
        when(periodService.eraseAppropriate(null, null, 3 + 5))
                .thenReturn(Period.builder()
                        .start(2)
                        .employee(Employee.builder().id(337L).build())
                        .date(date2)
                        .build());
        doReturn(updated1).when(spyAS).update(updated1);
        doReturn(updated2).when(spyAS).update(updated2);

        List<Assignment> updatedAssignments = spyAS.sendWithOrder(1L);

        verify(spyAS, times(2)).update(any());
        verify(periodService).eraseAppropriate(null, null, 2 + 4);
        verify(periodService).eraseAppropriate(null, null, 3 + 5);
        assertEquals(updated1, updatedAssignments.get(0));
        assertEquals(updated2, updatedAssignments.get(1));
    }

    @Test
    public void updateFull() {
        AssignmentService spyAS = spy(assignmentService);
        Assignment givenAssignment = Assignment.builder()
                .id(1L)
                .status(AssignmentStatus.ACCEPTED)
                .order(Order.builder().id(3L).build())
                .startTime(LocalDateTime.MAX)
                .finalCost(BigDecimal.valueOf(9.99))
                .employee(new Employee(2L, "employee_name", Specialization.CLEANER))
                .userCommentary("blah blah from user")
                .employeeCommentary("blah blah")
                .tasks(List.of(
                        Task.builder().id(3L).build(),
                        Task.builder().id(4L).build(),
                        Task.builder().id(5L).build()
                ))
                .build();
        doReturn(Assignment.builder().id(1L).build()).when(spyAS).get(1L);

        Assignment returnedAssignment = spyAS.update(givenAssignment);

        assertNull(returnedAssignment.getOrder());
        givenAssignment.setOrder(null);
        assertEquals(givenAssignment, returnedAssignment);
        verify(spyAS).get(1L);
        verify(assignmentDao).update(any());
    }

    @Test
    public void updateNothing() {
        AssignmentService spyAS = spy(assignmentService);
        Assignment givenAssignment = Assignment.builder().id(1L).build();
        doReturn(Assignment.builder().id(1L).build()).when(spyAS).get(1L);

        Assignment returnedAssignment = spyAS.update(givenAssignment);

        assertEquals(givenAssignment, returnedAssignment);
        verify(spyAS).get(1L);
        verify(assignmentDao).update(any());
    }

    @Test
    public void updateNonExisting() {
        AssignmentService spyAS = spy(assignmentService);
        Assignment givenAssignment = Assignment.builder().id(1L).build();

        doThrow(ResourceNotFoundException.class).when(spyAS).get(1L);

        assertThrows(ResourceNotFoundException.class, () -> spyAS.update(givenAssignment));
        verify(spyAS).get(1L);
        verify(assignmentDao, never()).update(any());
    }

    @Test
    public void getExisting() {
        Assignment assignment = Assignment.builder()
                .id(1L)
                .status(AssignmentStatus.DONE)
                .order(Order.builder()
                        .id(1L)
                        .user(User.builder()
                                .id(1L)
                                .password("pass")
                                .role(Role.USER)
                                .name("userName")
                                .email("e@mail.com")
                                .build())
                        .status(OrderStatus.DONE)
                        .arrivalTime(LocalDateTime.MAX)
                        .createdAt(LocalDateTime.MAX)
                        .finishedAt(LocalDateTime.MAX)
                        .build())
                .tasks(List.of(Task.builder().id(1L).build(), Task.builder().id(2L).build(), Task.builder().id(3L).build()))
                .userCommentary("blah blah")
                .finalCost(BigDecimal.valueOf(123))
                .startTime(LocalDateTime.MAX)
                .employeeCommentary("blah blah")
                .employee(Employee.builder().id(1L).build())
                .build();
        when(assignmentDao.findById(1L)).thenReturn(Optional.of(assignment));
        when(taskService.getTasks(1L)).thenReturn(List.of(Task.builder().id(1L).build(), Task.builder().id(2L).build(), Task.builder().id(3L).build()));

        Assignment returnedAssignment = assignmentService.get(1L);

        verify(assignmentDao).findById(1L);
        verify(taskService).getTasks(1L);
        assertEquals(assignment.getId(), returnedAssignment.getId());
        assertEquals(assignment, returnedAssignment);
    }

    @Test
    public void getNonExisting() {
        when(assignmentDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> assignmentService.get(1L));

        verify(assignmentDao, times(1)).findById(1L);
        verify(taskService, never()).getTasks(anyLong());
    }

    @Test
    public void getAllByOrderIdAtLeastOne() {
        Assignment assignment1 = Assignment.builder()
                .id(4L)
                .status(AssignmentStatus.DONE)
                .order(Order.builder()
                        .id(1L)
                        .user(User.builder()
                                .id(1L)
                                .password("pass")
                                .role(Role.USER)
                                .name("userName")
                                .email("e@mail.com")
                                .build())
                        .status(OrderStatus.DONE)
                        .arrivalTime(LocalDateTime.MAX)
                        .createdAt(LocalDateTime.MAX)
                        .finishedAt(LocalDateTime.MAX)
                        .build())
                .tasks(List.of(Task.builder().id(1L).build(), Task.builder().id(2L).build(), Task.builder().id(3L).build()))
                .userCommentary("blah blah")
                .finalCost(BigDecimal.valueOf(123))
                .startTime(LocalDateTime.MAX)
                .employeeCommentary("blah blah")
                .employee(Employee.builder().id(1L).build())
                .build();
        Assignment assignment2 = Assignment.builder()
                .id(5L)
                .status(AssignmentStatus.DONE)
                .order(Order.builder()
                        .id(1L)
                        .user(User.builder()
                                .id(1L)
                                .password("pass")
                                .role(Role.USER)
                                .name("userName")
                                .email("e@mail.com")
                                .build())
                        .status(OrderStatus.DONE)
                        .arrivalTime(LocalDateTime.MAX)
                        .createdAt(LocalDateTime.MAX)
                        .finishedAt(LocalDateTime.MAX)
                        .build())
                .tasks(List.of(Task.builder().id(1L).build(), Task.builder().id(2L).build(), Task.builder().id(3L).build()))
                .userCommentary("blah blah")
                .finalCost(BigDecimal.valueOf(123))
                .startTime(LocalDateTime.MAX)
                .employeeCommentary("blah blah")
                .employee(Employee.builder().id(1L).build())
                .build();
        when(assignmentDao.findAllByOrderId(1L)).thenReturn(List.of(assignment1, assignment2));
        when(taskService.getTasks(4L)).thenReturn(List.of(Task.builder().id(1L).build(), Task.builder().id(2L).build(), Task.builder().id(3L).build()));
        when(taskService.getTasks(5L)).thenReturn(List.of(Task.builder().id(1L).build(), Task.builder().id(2L).build(), Task.builder().id(3L).build()));

        List<Assignment> assignmentList = assignmentService.getAllByOrderId(1L);

        verify(assignmentDao).findAllByOrderId(1L);
        verify(taskService, times(2)).getTasks(anyLong());
        assertEquals(assignment1, assignmentList.get(0));
        assertEquals(assignment2, assignmentList.get(1));
        assertEquals(assignmentList.size(), 2);
    }

    @Test
    public void getAllByOrderIdNone() {
        when(assignmentDao.findAllByOrderId(1L)).thenReturn(new ArrayList<>());

        List<Assignment> assignmentList = assignmentService.getAllByOrderId(1L);
        verify(assignmentDao).findAllByOrderId(1L);
        verify(taskService, never()).getTasks(anyLong());

        assertEquals(assignmentList.size(), 0);
    }

    @Test
    public void acceptAccepted() {
        AssignmentService spyAS = spy(assignmentService);
        Assignment givenAssignment = Assignment.builder().id(1L).build();
        Assignment acceptedAssignment = Assignment.builder().status(AssignmentStatus.ACCEPTED).build();

        doReturn(acceptedAssignment).when(spyAS).get(1L);

        assertThrows(IllegalActionException.class, () -> spyAS.accept(givenAssignment));
        verify(spyAS, never()).update(any());
        verify(spyAS).get(anyLong());
    }

    @Test
    public void acceptNotAccepted() {
        AssignmentService spyAS = spy(assignmentService);
        Assignment givenAssignment = Assignment.builder()
                .id(1L)
                .finalCost(BigDecimal.valueOf(9.99))
                .employeeCommentary("blah blah")
                .build();
        Assignment notAcceptedAssignment = Assignment.builder()
                .status(AssignmentStatus.UNDER_CONSIDERATION)
                .build();

        doReturn(notAcceptedAssignment).when(spyAS).get(1L);
        doAnswer(invocation -> invocation.getArgument(0))
                .when(spyAS)
                .update(any());

        Assignment assignment = spyAS.accept(givenAssignment);

        givenAssignment.setStatus(AssignmentStatus.ACCEPTED);
        assertEquals(assignment, givenAssignment);

        verify(spyAS).get(anyLong());
        verify(spyAS).update(any());
    }

    @Test
    public void acceptNonExisting() {
        AssignmentService spyAS = spy(assignmentService);
        Assignment givenAssignment = Assignment.builder()
                .id(1L)
                .finalCost(BigDecimal.valueOf(9.99))
                .employeeCommentary("blah blah")
                .build();

        doThrow(ResourceNotFoundException.class).when(spyAS).get(1L);

        assertThrows(ResourceNotFoundException.class, () -> spyAS.accept(givenAssignment));

        verify(spyAS).get(anyLong());
        verify(spyAS, never()).update(any());
    }

}