package com.gznznzjsn.carservice.service.impl;

import com.gznznzjsn.carservice.dao.UserDao;
import com.gznznzjsn.carservice.domain.exception.UniqueResourceException;
import com.gznznzjsn.carservice.domain.user.User;
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
        return userDao.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " doesn't exist!"));
    }

    @Override
    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userDao.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }

    @Override
    @Transactional
    public User create(User user) {
        if(userDao.findByEmail(user.getEmail()).isPresent()){
            throw new UniqueResourceException("User with email "+ user.getEmail()+" already exists!");
        }
        userDao.create(user);
        return user;
    }

}
