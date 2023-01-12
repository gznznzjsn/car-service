package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.User;
import com.gznznzjsn.carservice.web.dto.UserDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface UserMapper {

    UserDto.Response.Read toReadDto(User entity);

    User toEntity(UserDto.Request.AddToOrder dto);
}
