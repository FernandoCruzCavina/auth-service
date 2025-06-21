package com.bank.auth_service.service;

import com.bank.auth_service.model.UserAuthenticated;

public interface JwtService {

    public String generateToken(UserAuthenticated user);

}
