package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.UserDao;
import com.gznznzjsn.carservice.domain.carservice.User;
import com.gznznzjsn.carservice.domain.exception.ResourceNotFoundException;
import com.gznznzjsn.carservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Override
    @Transactional(readOnly = true)
    public User get(Long userId) {
        return userDao.read(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " doesn't exist!"));
    }

}
