package com.bank.auth_service.service;

import java.util.UUID;

import com.bank.auth_service.dto.AuthenticationTokenDto;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.exception.InvalidUserCredentialsException;
import com.bank.auth_service.exception.RefreshTokenExpiredException;
import com.bank.auth_service.exception.RefreshTokenNotFoundException;
import com.bank.auth_service.exception.UserNotFoundException;
import com.bank.auth_service.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Service interface for handling authentication-related operations,
 * including user login with email and password, and login with token refresh functionality.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @see UserRepository
 * @since 1.0.0
 */
public interface AuthenticationService {

    /**
     * Authenticates a user using provided credentials and generates authentication tokens.
     * 
     * @param loginUserDto the login credentials including email and password
     * @param request the HTTP servlet request containing client metadata (IP, user agent)
     * @return {@link AuthenticationTokenDto} containing the JWT and refresh tokens
     * @throws InvalidUserCredentialsException if the credentials are invalid
     * @throws UserNotFoundException if the user does not exist
     * @see JwtService#generateToken(com.bank.auth_service.model.UserAuthenticated)
     * @see RefreshTokenService#createRefreshToken(String, String, User)
     */
    AuthenticationTokenDto login(LoginUserDto loginUserDto, HttpServletRequest request);

    /**
     * Issues a new authentication token using a valid refresh token.
     * 
     * <p>
     * If the client's IP address or user agent differs significantly from the original login,
     * the system may trigger a security alert to the user's email.
     * </p>
     *
     * @param refreshToken the UUID of the refresh token issued at login
     * @param request the HTTP servlet request containing client metadata
     * @return a new JWT authentication token as a String
     * @throws RefreshTokenNotFoundException if the refresh token does not exist
     * @throws RefreshTokenExpiredException if the refresh token has expired
     * @see RefreshTokenService#validateRefreshToken(UUID)
     */
    String refreshToken(UUID refreshToken, HttpServletRequest request);
}