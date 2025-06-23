package com.bank.auth_service.exception;

/**
 * Exception thrown when user credentials are invalid during authentication.
 * Used to indicate that the login or password is incorrect.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
public class InvalidUserCredentialsException extends RuntimeException {

    /**
     * Constructs a new InvalidUserCredentialsException with a default error message.
     */
    public InvalidUserCredentialsException() {
        super("Credenciais de usuário inválidas");
    }
}
