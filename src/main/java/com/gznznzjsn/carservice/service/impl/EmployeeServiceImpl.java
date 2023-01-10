package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.EmployeeDao;
import com.gznznzjsn.carservice.domain.carservice.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.domain.carservice.Period;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.EmployeeService;
import com.gznznzjsn.carservice.service.PeriodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeDao employeeDao;

    @Override
    @Transactional(readOnly = true)
    public List<Employee> readAllEmployees() {
        return employeeDao.readAll();
    }

    @Override
    @Transactional
    public Employee createEmployee(Employee employee) {
        return employeeDao.createEmployee(employee);
    }

    @Override
    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeDao.readEmployee(employeeId);
        if (optionalEmployee.isEmpty()) {
            throw new ResourceNotFoundException("Employee with id=" + employeeId + " not found!");
        }
        return optionalEmployee.get();
    }


}
