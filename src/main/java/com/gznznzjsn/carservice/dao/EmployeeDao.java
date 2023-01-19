package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeDao {

    List<Employee> readAll();

    void create(Employee employee);

    Optional<Employee> read(Long employeeId);

    void delete(Long employeeId);

}
