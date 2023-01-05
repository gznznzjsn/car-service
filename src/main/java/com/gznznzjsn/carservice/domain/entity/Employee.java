package com.gznznzjsn.carservice.domain.entity;

import com.gznznzjsn.carservice.domain.enums.Specialization;
import lombok.Data;

import java.util.List;

/*
TODO
consider the annotations for entities
think over the specialization
 */
@Data
public class Employee {
    private final long id;
    private String name;
    private List<Specialization> specializations;


}
