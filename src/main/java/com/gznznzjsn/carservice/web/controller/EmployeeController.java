package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.service.EmployeeService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentMapper;
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
    private final AssignmentMapper assignmentMapper;
    private final EmployeeService employeeService;
    private final AssignmentService assignmentService;

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

    @GetMapping("/{employeeId}/assignments/{assignmentId}")
    public @Valid AssignmentDto.Response.Read getAssignment(
            @PathVariable Long employeeId,
            @PathVariable Long assignmentId) {
        //todo check employeeId
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        return assignmentMapper.toReadDto(assignment);
    }

//    @PatchMapping("/{id}/assignments/{assignment_id}")
//    public Assignment acceptAssignment(@PathVariable("id") Long employeeId, @PathVariable Long assignment_id, @RequestBody AssignmentDto.Request.Accept assignmentDto) {
//        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
//        assignment.setEmployee(employeeService.getEmployeeById(employeeId));
//        assignment.setId(assignment_id);
//        Assignment acceptedAssignment = assignmentService.acceptAssignment(assignment);
//        return acceptedAssignment;
//        //return assignmentMapper.toDto(acceptedAssignment);
//    }

}
