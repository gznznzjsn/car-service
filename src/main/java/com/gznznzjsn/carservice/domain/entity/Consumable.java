package com.gznznzjsn.carservice.domain.entity;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class Consumable {
    private final long id;
    private final String name;
    private String cost;
    private long quantity;
    private List<Task> tasks;
}
