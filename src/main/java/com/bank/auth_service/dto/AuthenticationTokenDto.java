package com.bank.auth_service.dto;

/**
 * DTO representing authentication tokens returned after a successful login.
 * 
 * <p>
 * Contains the access token (JWT) used for authorization and the refresh token used to obtain new access tokens.
 * </p>
 * 
 * @param token the JWT access token
 * @param refreshToken the refresh token used to renew the access token
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
public record AuthenticationTokenDto(
    String token,
    String refreshToken
) {
}
