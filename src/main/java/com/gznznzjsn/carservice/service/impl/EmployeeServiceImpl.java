package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.EmployeeDao;
import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;

    @Override
    public List<Employee> readAllEmployees() {
        return employeeDao.readAll();
    }

    @Override
    public Employee createEmployee(Employee employee) {
        return employeeDao.createEmployee(employee);
    }

    @Override
    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeDao.readEmployeeById(employeeId);
        if(optionalEmployee.isEmpty()){
            throw new ResourceNotFoundException("Employee with id="+employeeId+" not found!");
        }
        return optionalEmployee.get();
    }
}
