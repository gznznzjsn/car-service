package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.user.User;
import com.gznznzjsn.carservice.web.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User entity);

    User toEntity(UserDto dto);

}
