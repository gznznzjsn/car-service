package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {
    List<Employee> readAll();

    Employee createEmployee(Employee employee);

    Optional<Employee> readEmployeeById(Long employeeId);
}
