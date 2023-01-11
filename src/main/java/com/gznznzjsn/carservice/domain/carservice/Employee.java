package com.gznznzjsn.carservice.domain.carservice;

import com.gznznzjsn.carservice.domain.carservice.enums.Specialization;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Employee {

    private Long id;
    private String name;
    private Specialization specialization;

}
