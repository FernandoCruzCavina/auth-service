package com.bank.auth_service.exception;

/**
 * Exception thrown when a refresh token is not found.
 * Used to indicate that the provided token does not exist in the system.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
public class RefreshTokenNotFoundException extends RuntimeException {

    /**
     * Constructs a new RefreshTokenNotFoundException with a default error message.
     */
    public RefreshTokenNotFoundException() {
        super("Refresh token não encontrado");
    }
}
