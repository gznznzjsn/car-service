package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.User;

import java.util.Optional;

public interface UserDao {

    Optional<User> readUser(Long userId);

}
