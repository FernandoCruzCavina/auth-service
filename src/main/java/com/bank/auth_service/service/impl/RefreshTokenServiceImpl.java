package com.bank.auth_service.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import com.bank.auth_service.exception.RefreshTokenExpiredException;
import com.bank.auth_service.exception.RefreshTokenNotFoundException;
import com.bank.auth_service.model.RefreshToken;
import com.bank.auth_service.model.User;
import com.bank.auth_service.repository.RefreshTokenRepository;
import com.bank.auth_service.service.RefreshTokenService;

public class RefreshTokenServiceImpl implements RefreshTokenService{

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository repository) {
        this.refreshTokenRepository = repository;
    }

    public String createRefreshToken(String ip, String userAgent, User user) {
        UUID token = UUID.randomUUID();
        long expirationDate = Instant.now().plusSeconds(refreshExpiration).toEpochMilli();

        RefreshToken refreshToken = new RefreshToken(token, expirationDate, ip, userAgent, user);
        refreshTokenRepository.save(refreshToken);

        return token.toString();
    }

    public RefreshToken validateRefreshToken(UUID token) {
        var stored = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(RefreshTokenNotFoundException::new);

        if(stored.getExpirationDate() >= Instant.now().toEpochMilli()){
            throw new RefreshTokenExpiredException();
        }

        return stored;
    }
}
