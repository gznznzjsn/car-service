package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.carservice.assignment.Assignment;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentMapper assignmentMapper;
    private final AssignmentService assignmentService;

    @GetMapping("/{assignmentId}")
    public @Valid AssignmentDto.Response.Read getAssignment(
            @PathVariable Long assignmentId) {
        Assignment assignment = assignmentService.getAssignment(assignmentId);
        return assignmentMapper.toReadDto(assignment);
    }

    @PostMapping
    public @Valid AssignmentDto.Response.Create addAssignment(
            @Valid @RequestBody AssignmentDto.Request.Create assignmentDto
    ) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        Assignment createdAssignment = assignmentService.createAssignment(assignment);
        return assignmentMapper.toCreateDto(createdAssignment);
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
