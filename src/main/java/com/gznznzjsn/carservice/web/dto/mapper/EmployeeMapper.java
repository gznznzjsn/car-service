package com.gznznzjsn.carservice.web.dto.mapper;

import com.gznznzjsn.carservice.domain.carservice.Employee;
import com.gznznzjsn.carservice.web.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
@Component
public interface EmployeeMapper {
    @Mapping(target = "id",expression = "java(null)")
    Employee toEntity(EmployeeDto.Request.Create dto);
    EmployeeDto.Response.Read toDto(Employee entity);
}
