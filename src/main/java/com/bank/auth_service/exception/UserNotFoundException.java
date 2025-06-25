package com.bank.auth_service.exception;

/**
 * Exception thrown when a user is not found in the system.
 * Used to indicate that the requested user does not exist.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Constructs a new UserNotFoundException with a default error message.
     */
    public UserNotFoundException() {
        super("Usuário não encontrado");
    }
}
