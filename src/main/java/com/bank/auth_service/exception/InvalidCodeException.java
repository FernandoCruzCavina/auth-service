package com.bank.auth_service.exception;

/**
 * Exception thrown when a provided verification code is invalid.
 * Used to indicate that the code entered by the user does not match the expected value.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
public class InvalidCodeException extends RuntimeException {

    /**
     * Constructs a new InvalidCodeException with a default error message.
     */
    public InvalidCodeException(){
        super("Esse código está incorreto");
    }
}
