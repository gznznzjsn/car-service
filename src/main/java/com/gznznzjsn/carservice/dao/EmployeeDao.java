package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeDao {

    List<Employee> findAll();

    void create(Employee employee);

    Optional<Employee> findById(Long employeeId);

    void delete(Long employeeId);

}
