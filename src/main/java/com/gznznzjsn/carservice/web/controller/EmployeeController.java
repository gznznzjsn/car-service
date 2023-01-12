package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.service.EmployeeService;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import com.gznznzjsn.carservice.web.dto.mapper.EmployeeMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;



    @GetMapping
    public List<EmployeeDto.Response.@Valid Read> getAllEmployees() {
        return employeeService.readAllEmployees().stream()
                .map(employeeMapper::toReadDto)
                .toList();
    }

    @PostMapping
    public @Valid EmployeeDto.Response.Read createEmployee(@Valid @RequestBody EmployeeDto.Request.Create employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee returnedEmployee = employeeService.createEmployee(employee);
        return employeeMapper.toReadDto(returnedEmployee);
    }

    @GetMapping("/{id}")
    public @Valid EmployeeDto.Response.Read getEmployee(@PathVariable("id") Long employeeId) {
        Employee returnedEmployee = employeeService.getEmployee(employeeId);
        return employeeMapper.toReadDto(returnedEmployee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") Long employeeId) {
        employeeService.deleteEmployee(employeeId);
    }




}
