package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface EmployeeMapper {
    Employee toEntity(EmployeeDto.Request.Create dto);
    EmployeeDto.Response.Read toReadDto(Employee entity);
}
