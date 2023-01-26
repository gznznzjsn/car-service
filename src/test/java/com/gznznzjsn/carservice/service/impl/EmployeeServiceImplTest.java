package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.EmployeeDao;
import com.gznznzjsn.carservice.domain.Employee;
import com.gznznzjsn.carservice.domain.Specialization;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    public void getAllAtLeastOne() {
        Employee employee1 = Employee.builder()
                .id(1L)
                .name("employeeName1")
                .specialization(Specialization.CLEANER)
                .build();
        Employee employee2 = Employee.builder()
                .id(2L)
                .name("employeeName2")
                .specialization(Specialization.REPAIRER)
                .build();
        when(employeeDao.findAll()).thenReturn(List.of(employee1, employee2));

        List<Employee> employeeList = employeeService.getAll();

        assertEquals(2, employeeList.size());
        assertEquals(employee1, employeeList.get(0));
        assertEquals(employee2, employeeList.get(1));
    }

    @Test
    public void getAllNone() {
        when(employeeDao.findAll()).thenReturn(new ArrayList<>());

        List<Employee> employeeList = employeeService.getAll();

        assertEquals(0, employeeList.size());
    }

    @Test
    public void create() {
        Employee employee = Employee.builder()
                .id(1L)
                .name("employeeName")
                .specialization(Specialization.CLEANER)
                .build();
        Employee employeeToCreate = Employee.builder()
                .name("employeeName")
                .specialization(Specialization.CLEANER)
                .build();
        doAnswer(invocation -> {
            ((Employee) invocation.getArgument(0)).setId(1L);
            return null;
        }).when(employeeDao).create(any());

        Employee returnedEmployee = employeeService.create(employee);

        verify(employeeDao).create(any());
        employeeToCreate.setId(1L);
        assertEquals(employeeToCreate, returnedEmployee);
    }

    @Test
    public void getNonExisting() {
        when(employeeDao.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> employeeService.get(1L));

        verify(employeeDao).findById(1L);
    }

    @Test
    public void getExisting() {
        Employee employee = Employee.builder()
                .id(1L)
                .name("employeeName")
                .specialization(Specialization.CLEANER)
                .build();
        when(employeeDao.findById(1L)).thenReturn(Optional.of(employee));

        Employee returnedEmployee = employeeService.get(1L);

        verify(employeeDao).findById(1L);
        assertEquals(employee, returnedEmployee);
    }

    @Test
    public void delete() {
        employeeService.delete(1L);

        verify(employeeDao).delete(1L);
    }
}