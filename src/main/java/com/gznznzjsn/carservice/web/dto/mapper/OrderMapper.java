package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.order.Order;
import com.gznznzjsn.carservice.web.dto.OrderDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {AssignmentMapper.class, UserMapper.class})
public interface OrderMapper {

    Order toEntity(OrderDto dto);

    OrderDto toDto(Order entity);

}
