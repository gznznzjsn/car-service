package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.Employee;

import java.util.List;


public interface EmployeeService {

    List<Employee> getAll();

    Employee create(Employee employee);

    Employee get(Long employeeId);

    void delete(Long employeeId);

}

