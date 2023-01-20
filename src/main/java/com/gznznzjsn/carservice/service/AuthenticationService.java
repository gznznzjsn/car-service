package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.AuthEntity;

public interface AuthenticationService {

    AuthEntity register(AuthEntity request);

    AuthEntity authenticate(AuthEntity request);

}
