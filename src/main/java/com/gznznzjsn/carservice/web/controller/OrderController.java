package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.entity.Employee;
import com.gznznzjsn.carservice.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final EmployeeService employeeService;
    @GetMapping("/employees")
    public List<Employee> showEmployees(){
        return employeeService.getAllEmployees();
    }
}
