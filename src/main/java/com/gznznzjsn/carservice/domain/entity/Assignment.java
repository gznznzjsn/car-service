package com.gznznzjsn.carservice.domain.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Assignment {
    private final long id;
    private final Employee employee;
    private final Task task;
}
