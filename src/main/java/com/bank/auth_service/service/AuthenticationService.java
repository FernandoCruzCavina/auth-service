package com.bank.auth_service.service;

import java.util.UUID;

import com.bank.auth_service.dto.AuthenticationTokenDto;
import com.bank.auth_service.dto.LoginUserDto;

import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    public AuthenticationTokenDto login(LoginUserDto loginUserDto, HttpServletRequest request);
    
    public String refreshToken(UUID refreshToken, HttpServletRequest request);
}
