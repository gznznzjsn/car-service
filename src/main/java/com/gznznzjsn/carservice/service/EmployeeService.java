package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.entity.Employee;

import java.util.List;


public interface EmployeeService {
    List<Employee> readAllEmployees();

    Employee createEmployee(Employee employee);

    Employee getEmployeeById(Long employeeId);
}

