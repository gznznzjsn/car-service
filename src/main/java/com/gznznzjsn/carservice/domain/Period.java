package com.gznznzjsn.carservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Period {

    private Long id;
    private Employee employee;
    private LocalDate date;
    private int start;
    private int end;

}
