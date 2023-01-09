package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.service.EmployeeService;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import com.gznznzjsn.carservice.web.dto.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    @GetMapping("/employees")
    public List<EmployeeDto.Response.Read> getAllEmployees() {
        return employeeService.readAllEmployees().stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @PostMapping("/employees")
    public EmployeeDto.Response.Read createEmployee(@RequestBody EmployeeDto.Request.Create employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee returnedEmployee = employeeService.createEmployee(employee);
        return employeeMapper.toDto(returnedEmployee);
    }

    @GetMapping("/employees/{id}")
    public EmployeeDto.Response.Read getEmployee(@PathVariable("id") Long employeeId) {
        Employee returnedEmployee = employeeService.getEmployeeById(employeeId);
        return employeeMapper.toDto(returnedEmployee);
    }
}
