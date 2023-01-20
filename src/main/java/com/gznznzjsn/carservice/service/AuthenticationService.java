package com.gznznzjsn.carservice.service;

import com.gznznzjsn.carservice.domain.AuthenticationRequest;
import com.gznznzjsn.carservice.domain.AuthenticationResponse;
import com.gznznzjsn.carservice.domain.RegisterRequest;

public interface AuthenticationService {

    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

}
