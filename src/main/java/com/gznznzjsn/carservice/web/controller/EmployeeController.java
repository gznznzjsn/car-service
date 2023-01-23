package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.Employee;
import com.gznznzjsn.carservice.service.EmployeeService;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import com.gznznzjsn.carservice.web.dto.group.OnCreateEmployee;
import com.gznznzjsn.carservice.web.dto.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @GetMapping
    public List<EmployeeDto> getAll() {
        return employeeService.getAll().stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @PostMapping
    public EmployeeDto create(@Validated(OnCreateEmployee.class) @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee returnedEmployee = employeeService.create(employee);
        return employeeMapper.toDto(returnedEmployee);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @GetMapping("/{id}")
    public EmployeeDto get(@PathVariable("id") Long employeeId) {
        Employee returnedEmployee = employeeService.get(employeeId);
        return employeeMapper.toDto(returnedEmployee);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long employeeId) {
        employeeService.delete(employeeId);
    }

}
