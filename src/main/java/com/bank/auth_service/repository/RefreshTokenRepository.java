package com.bank.auth_service.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.auth_service.model.RefreshToken;


/**
 * Repository interface for managing {@link RefreshToken} entities.
 * Handles access to refresh token data.
 *
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @see RefreshToken
 * @since 1.0
 */
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID>{

    /**
     * Finds a refresh token by its UUID value.
     *
     * @param refreshToken the UUID of the refresh token
     * @return an {@link Optional} containing the token, if found
     */
    Optional<RefreshToken> findByRefreshToken(UUID refreshToken);
}
