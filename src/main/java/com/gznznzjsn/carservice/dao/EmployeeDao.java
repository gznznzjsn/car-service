package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> readAll();

    Employee create(Employee employee);

    Optional<Employee> read(Long employeeId);

    void delete(Long employeeId);

}
