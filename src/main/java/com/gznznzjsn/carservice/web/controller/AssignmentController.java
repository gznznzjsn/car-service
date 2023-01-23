package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.assignment.Assignment;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.group.OnAccept;
import com.gznznzjsn.carservice.web.dto.group.OnCreateAssignment;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AssignmentController {

    private final AssignmentMapper assignmentMapper;
    private final AssignmentService assignmentService;

    @PreAuthorize("@securityChecks.hasAssignment(#userId,#orderId,#assignmentId)")
    @GetMapping("users/{userId}/orders/{orderId}/assignments/{assignmentId}")
    public AssignmentDto get(@PathVariable Long userId, @PathVariable Long orderId, @PathVariable Long assignmentId) {
        Assignment assignment = assignmentService.get(assignmentId);
        return assignmentMapper.toDto(assignment);
    }

    @PreAuthorize("@securityChecks.hasOrder(#userId,#orderId)" +
                  "&& assignmentDto.order().id().equals(#orderId)" +
                  "&& assignmentDto.order().user().id().equals(#userId)")
    @PostMapping("users/{userId}/orders/{orderId}/assignments")
    public AssignmentDto create(@Validated(OnCreateAssignment.class) @RequestBody AssignmentDto assignmentDto, @PathVariable Long orderId, @PathVariable Long userId) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        Assignment createdAssignment = assignmentService.create(assignment);
        return assignmentMapper.toDto(createdAssignment);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @PatchMapping("assignments/")
    public AssignmentDto accept(@Validated(OnAccept.class) @RequestBody AssignmentDto assignmentDto) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        Assignment acceptedAssignment = assignmentService.accept(assignment);
        return assignmentMapper.toDto(acceptedAssignment);
    }

}
