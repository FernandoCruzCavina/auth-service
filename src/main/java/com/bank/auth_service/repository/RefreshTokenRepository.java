package com.bank.auth_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.auth_service.model.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID>{

    Optional<RefreshToken> findByRefreshToken(UUID refreshToken);
}
