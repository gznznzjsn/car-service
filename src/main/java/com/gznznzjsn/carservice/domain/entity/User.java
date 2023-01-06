package com.gznznzjsn.carservice.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class User {

    private Long id;

    private String username;
    private List<Order> orders;

}
