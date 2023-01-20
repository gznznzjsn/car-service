package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.assignment.Assignment;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.group.OnAccept;
import com.gznznzjsn.carservice.web.dto.group.OnCreateAssignment;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assignments")
public class AssignmentController {

    private final AssignmentMapper assignmentMapper;
    private final AssignmentService assignmentService;

    @GetMapping("/{assignmentId}")
    public AssignmentDto get(@PathVariable Long assignmentId) {
        Assignment assignment = assignmentService.get(assignmentId);
        return assignmentMapper.toDto(assignment);
    }

    @PostMapping
    public AssignmentDto create(@Validated(OnCreateAssignment.class) @RequestBody AssignmentDto assignmentDto) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        Assignment createdAssignment = assignmentService.create(assignment);
        return assignmentMapper.toDto(createdAssignment);
    }

    @PatchMapping
    public AssignmentDto accept(@Validated(OnAccept.class) @RequestBody AssignmentDto assignmentDto) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        Assignment acceptedAssignment = assignmentService.accept(assignment);
        return assignmentMapper.toDto(acceptedAssignment);
    }

}
