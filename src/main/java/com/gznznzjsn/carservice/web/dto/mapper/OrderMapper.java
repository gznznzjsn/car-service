package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Order;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class})
@Component
public interface OrderMapper {
    @Mapping(target = "arrivalTime", source = "dto.arrivalTime")
    @Mapping(target = "assignments", source = "dto.requestedAssignments")
    Order toEntity(OrderDto.Request.Create dto);

    @Mapping(target = "precalculatedAssignments", source = "entity.assignments")
    @Mapping(target = "finishTime", expression = "java(com.gznznzjsn.carservice.service.OrderService.calculateFinishTime(entity))")
    OrderDto.Response.ReadPrecalculated toDto(Order entity);
}
