package com.gznznzjsn.carservice.domain.entity;

import com.gznznzjsn.carservice.domain.enums.Specialization;
import lombok.Data;

import java.util.List;

@Data
public class Employee {
    private final long id;
    private List<Specialization> specializations;


}
