package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.entity.Employee;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface EmployeeMapper {
    Employee toEntity(EmployeeDto dto);
    EmployeeDto toDto(Employee entity);
}
