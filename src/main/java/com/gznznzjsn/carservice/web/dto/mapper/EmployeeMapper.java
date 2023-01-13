package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeDto  dto);
    EmployeeDto toDto(Employee entity);

}
