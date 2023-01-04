package com.gznznzjsn.carservice.domain.entity;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class User {

    private final long id;

    private String username;
    private List<Order> orders;

}
