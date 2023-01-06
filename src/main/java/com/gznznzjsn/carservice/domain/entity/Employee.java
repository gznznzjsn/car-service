package com.gznznzjsn.carservice.domain.entity;

import com.gznznzjsn.carservice.domain.enums.Specialization;
import lombok.Builder;
import lombok.Data;

/*
TODO
consider the annotations for entities
 */
@Data
@Builder
public class Employee {
    private Long id;
    private String name;
    private Specialization specialization;


}
