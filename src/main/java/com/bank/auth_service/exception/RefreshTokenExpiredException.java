package com.bank.auth_service.exception;

/**
 * Exception thrown when a refresh token has expired.
 * Used to indicate that the token is no longer valid for refreshing authentication.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
public class RefreshTokenExpiredException extends RuntimeException {

    /**
     * Constructs a new RefreshTokenExpiredException with a default error message.
     */
    public RefreshTokenExpiredException() {
        super("O refresh token está expirado");
    }
}
