package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.Employee;
import com.gznznzjsn.carservice.service.EmployeeService;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import com.gznznzjsn.carservice.web.dto.group.OnCreateEmployee;
import com.gznznzjsn.carservice.web.dto.mapper.EmployeeMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/car-service/employees")
@SecurityRequirement(name = "Bearer Authentication")

public class EmployeeController {

    private final EmployeeMapper employeeMapper;
    private final EmployeeService employeeService;

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @GetMapping
    @Operation(
            tags = {"Employees"},
            summary = "Get all employees",
            description = "List of all registered employees will be returned",
            responses = {
                    @ApiResponse(responseCode = "200", description = "All employees successfully returned",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDto.class))
                    )
            }
    )
    public List<EmployeeDto> getAll() {
        return employeeService.getAll().stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @PostMapping
    @Operation(
            tags = {"Employees"},
            summary = "Create employee",
            description = "Fulfill name and specialization in request body to register new employee",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee successfully registered",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDto.class))
                    )
            }
    )
    public EmployeeDto create(@Validated(OnCreateEmployee.class) @RequestBody EmployeeDto employeeDto) {
        Employee employee = employeeMapper.toEntity(employeeDto);
        Employee returnedEmployee = employeeService.create(employee);
        return employeeMapper.toDto(returnedEmployee);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @GetMapping("/{employeeId}")
    @Operation(
            tags = {"Employees"},
            summary = "Get employee",
            description = "Enter id of employee, you want to obtain",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee successfully returned",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDto.class))
                    )
            },
            parameters = @Parameter(name = "employeeId", example = "3")
    )
    public EmployeeDto get(@PathVariable Long employeeId) {
        Employee returnedEmployee = employeeService.get(employeeId);
        return employeeMapper.toDto(returnedEmployee);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @DeleteMapping("/{employeeId}")
    @Operation(
            tags = {"Employees"},
            summary = "Delete employee",
            description = "Enter id of employee, you want to delete",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Employee successfully deleted",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeDto.class))
                    )
            },
            parameters = @Parameter(name = "employeeId", example = "3")
    )
    public void delete(@PathVariable Long employeeId) {
        employeeService.delete(employeeId);
    }

}
