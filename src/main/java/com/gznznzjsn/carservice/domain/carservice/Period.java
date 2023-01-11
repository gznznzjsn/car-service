package com.gznznzjsn.carservice.domain.carservice;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Period {

    private Long id;
    private Employee employee;
    private LocalDate date;
    private int start;
    private int end;

}
