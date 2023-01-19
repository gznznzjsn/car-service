package com.gznznzjsn.carservice.dao;

import com.gznznzjsn.carservice.domain.carservice.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserDao {

    Optional<User> read(Long userId);

}
