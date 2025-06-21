package com.bank.auth_service.service;

import java.util.UUID;

import com.bank.auth_service.model.RefreshToken;
import com.bank.auth_service.model.User;

public interface RefreshTokenService {

    public String createRefreshToken(String ip, String userAgent, User user);

    public RefreshToken validateRefreshToken(UUID token);
}

