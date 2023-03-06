package com.gznznzjsn.carservice.web.controller;

import com.gznznzjsn.carservice.domain.assignment.Assignment;
import com.gznznzjsn.carservice.domain.order.Order;
import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.service.AssignmentService;
import com.gznznzjsn.carservice.web.dto.AssignmentDto;
import com.gznznzjsn.carservice.web.dto.group.OnAccept;
import com.gznznzjsn.carservice.web.dto.group.OnCreateAssignment;
import com.gznznzjsn.carservice.web.dto.mapper.AssignmentMapper;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/car-service")
@SecurityRequirement(name = "Bearer Authentication")
public class AssignmentController {

    private final AssignmentMapper assignmentMapper;
    private final AssignmentService assignmentService;

    @PreAuthorize("@securityChecks.hasAssignment(#userId,#orderId,#assignmentId)")
    @GetMapping("users/{userId}/orders/{orderId}/assignments/{assignmentId}")
    @Operation(
            tags = {"Assignments"},
            summary = "Get assignment",
            description = "Enter assignment id with ids of order and user it belongs to, in order to get required assignment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Assignment successfully returned",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssignmentDto.class))
                    )
            },
            parameters = {
                    @Parameter(name = "userId", example = "1"),
                    @Parameter(name = "orderId", example = "1"),
                    @Parameter(name = "assignmentId", example = "1")

            }
    )
    public AssignmentDto get(@PathVariable Long userId, @PathVariable Long orderId, @PathVariable Long assignmentId) {
        Assignment assignment = assignmentService.get(assignmentId);
        return assignmentMapper.toDto(assignment);
    }

    @PreAuthorize("@securityChecks.hasOrder(#userId,#orderId)")
    @PostMapping("users/{userId}/orders/{orderId}/assignments")
    @Operation(
            tags = {"Assignments"},
            summary = "Create assignment",
            description = "Enter ids of user and his order, to which you want to attach an assignment, sent in request body",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Assignment successfully created",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssignmentDto.class))
                    )
            },
            parameters = {
                    @Parameter(name = "userId", example = "1"),
                    @Parameter(name = "orderId", example = "1")

            }
    )
    public AssignmentDto create(@Validated(OnCreateAssignment.class) @RequestBody AssignmentDto assignmentDto, @PathVariable Long orderId, @PathVariable Long userId) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        assignment.setOrder(Order.builder()
                .id(orderId)
                .user(User.builder()
                        .id(userId)
                        .build())
                .build());
        Assignment createdAssignment = assignmentService.create(assignment);
        return assignmentMapper.toDto(createdAssignment);
    }

    @PreAuthorize("hasAuthority('EMPLOYEE_MANAGER')")
    @PatchMapping("assignments/{assignmentId}")
    @Operation(
            tags = {"Assignments"},
            summary = "Accept assignment",
            description = "Enter the id of assignment, you want to accept, final cost and employee commentary. Chosen assignment with its final cost will be marked as accepted",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Assignment successfully accepted",
                            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AssignmentDto.class))
                    )
            },
            parameters = @Parameter(name = "assignmentId", example = "1")
    )
    public AssignmentDto accept(@Validated(OnAccept.class) @RequestBody AssignmentDto assignmentDto, @PathVariable Long assignmentId) {
        Assignment assignment = assignmentMapper.toEntity(assignmentDto);
        assignment.setId(assignmentId);
        Assignment acceptedAssignment = assignmentService.accept(assignment);
        return assignmentMapper.toDto(acceptedAssignment);
    }

}
