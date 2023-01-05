package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.entity.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> fetchAll();
}
