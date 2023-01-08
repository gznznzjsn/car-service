package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.service.EmployeeService;
import com.gznznzjsn.carservice.web.dto.mapper.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

//    @GetMapping("/employees")
//    public List<EmployeeDto> getAllEmployees() {
//        return employeeService.readAllEmployees().stream()
//                .map(employeeMapper::toDto)
//                .toList();
//    }
//
//    @PostMapping("/employees")
//    public EmployeeDto createEmployee(@RequestBody EmployeeDto employeeDto) {
//        Employee employee = employeeMapper.toEntity(employeeDto);
//        Employee returnedEmployee = employeeService.createEmployee(employee);
//        return employeeMapper.toDto(returnedEmployee);
//    }

//    @GetMapping("/employees/{id}")
//    public EmployeeDto getEmployee(@PathVariable("id") Long employeeId) {
//        Employee returnedEmployee = employeeService.getEmployeeById(employeeId);
//        return employeeMapper.toDto(returnedEmployee);
//    }
}
