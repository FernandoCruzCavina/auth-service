package com.bank.auth_service.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.auth_service.dto.AuthenticationTokenDto;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.exception.InvalidUserCredentialsException;
import com.bank.auth_service.exception.RefreshTokenExpiredException;
import com.bank.auth_service.exception.RefreshTokenNotFoundException;
import com.bank.auth_service.exception.UserNotFoundException;
import com.bank.auth_service.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;
/** 
 * Controller for handling authentication-related requests.
 * Provides endpoints for user login and token refresh operations.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @see AuthenticationService
 * @since 1.0.0
 */
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticates a user with email and password, generating authentication tokens.
     * 
     * @param loginUserDto the login credentials (email and password)
     * @param httpServletRequest the HTTP request (used to capture IP and user-agent)
     * @return {@link AuthenticationTokenDto} containing the JWT access token and refresh token
     * @throws InvalidUserCredentialsException if the provided credentials are invalid
     * @throws UserNotFoundException if the user does not exist
     */
    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokenDto> authenticate(@RequestBody LoginUserDto loginUserDto, HttpServletRequest httpServletRequest) {
        AuthenticationTokenDto tokens = authenticationService.login(loginUserDto, httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK).body(tokens);
    }

    /**
     * Refreshes the authentication token using a valid refresh token.
     * 
     * @param refreshToken the UUID of the refresh token issued at login
     * @param httpServletRequest the HTTP servlet request containing client metadata
     * @return new JWT authentication token as a String
     * @throws RefreshTokenNotFoundException if the refresh token does not exist
     * @throws RefreshTokenExpiredException if the refresh token has expired
     */
    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody UUID refreshToken, HttpServletRequest httpServletRequest) {
        String newToken = authenticationService.refreshToken(refreshToken, httpServletRequest);
        
        return ResponseEntity.status(HttpStatus.OK).body(newToken);
    }
}
