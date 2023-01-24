package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.user.User;

public interface UserService {

    User get(Long userId);

    User getByEmail(String email);

    User create(User user);
}
