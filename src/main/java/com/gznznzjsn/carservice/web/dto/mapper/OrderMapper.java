package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.order.Order;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class, UserMapper.class})
@Component
public interface OrderMapper {

    Order toEntity(OrderDto.Request.Create dto);

    //    @Mapping(target = "finishTime", expression = "java(com.gznznzjsn.carservice.service.OrderService.calculateFinishTime(entity))")
    OrderDto.Response.ReadPrecalculated toDto(Order entity);

    OrderDto.Response.Create toCreateDto(Order entity);

    OrderDto.Response.Read toReadDto(Order order);
}
