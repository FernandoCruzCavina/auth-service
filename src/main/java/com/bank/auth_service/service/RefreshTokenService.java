package com.bank.auth_service.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bank.auth_service.model.RefreshToken;
import com.bank.auth_service.model.User;
import com.bank.auth_service.repository.RefreshTokenRepository;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository repository) {
        this.refreshTokenRepository = repository;
    }

    public String createRefreshToken(String ip, String userAgent, User user) {
        UUID token = UUID.randomUUID();
        long expirationDate = Instant.now().plusSeconds(refreshExpiration).toEpochMilli();

        RefreshToken refreshToken = new RefreshToken(expirationDate, ip, userAgent, user);
        refreshTokenRepository.save(refreshToken);

        return token.toString();
    }

    public RefreshToken validateRefreshToken(UUID token) {
        var stored = refreshTokenRepository.findByRefreshToken(token)
                .orElseThrow(() -> new RuntimeException("not have this refreshToken in this service"));

        if(stored.getExpirationDate() >= Instant.now().toEpochMilli()){
            throw new RuntimeException("this token already have expired");
        }

        return stored;
    }
}

