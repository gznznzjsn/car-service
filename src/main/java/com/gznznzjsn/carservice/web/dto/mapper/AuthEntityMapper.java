package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.user.AuthEntity;
import com.gznznzjsn.carservice.web.dto.AuthEntityDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface AuthEntityMapper {

    AuthEntity toEntity(AuthEntityDto dto);

    AuthEntityDto toDto(AuthEntity entity);

}
