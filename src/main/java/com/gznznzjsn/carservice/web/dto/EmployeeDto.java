package com.gznznzjsn.carservice.web.dto;

import com.gznznzjsn.carservice.domain.enums.Specialization;
import lombok.AllArgsConstructor;
import lombok.Getter;
/*
* todo
*  validation
* */
@AllArgsConstructor
@Getter
public class EmployeeDto {

    private final Long id;
    private final String name;
    private final Specialization specialization;

}
