package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface EmployeeMapper {
    Employee toEntity(EmployeeDto.Response.Create dto);
    EmployeeDto.Response.Create toDto(Employee entity);
}
