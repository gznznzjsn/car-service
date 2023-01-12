package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.carservice.Employee;

import java.util.List;


public interface EmployeeService {
    List<Employee> readAllEmployees();

    Employee createEmployee(Employee employee);

    Employee getEmployee(Long employeeId);

    void deleteEmployee(Long employeeId);
}

