package com.bank.auth_service.service;

import com.bank.auth_service.exception.RefreshTokenExpiredException;
import com.bank.auth_service.exception.RefreshTokenNotFoundException;
import com.bank.auth_service.model.RefreshToken;
import com.bank.auth_service.model.User;
import com.bank.auth_service.repository.RefreshTokenRepository;

import java.util.UUID;

/**
 * Service interface for operations related to refresh tokens.
 * Provides methods to create and validate refresh tokens associated with a user session.
 * <p>
 * A refresh token allows users to obtain new JWT tokens without logging in again.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @see RefreshTokenRepository
 * @since 1.0
 */
public interface RefreshTokenService {

    /**
     * Creates a new refresh token valid for 7 days.
     * Associates the token with the user, and stores session metadata such as IP address and user agent.
     *
     * @param ip the client's IP address
     * @param userAgent the client's user agent string
     * @param user the user for whom the refresh token is created
     * @return the created refresh token as a String
     */
    String createRefreshToken(String ip, String userAgent, User user);


    /**
     * Validates the provided refresh token.
     * Checks if the token exists and has not expired.
     *
     * @param refreshToken the UUID of the refresh token
     * @return the corresponding {@link RefreshToken} if valid
     * @throws RefreshTokenNotFoundException if the token does not exist
     * @throws RefreshTokenExpiredException  if the token has expired
     */
    RefreshToken validateRefreshToken(UUID refreshToken);
}