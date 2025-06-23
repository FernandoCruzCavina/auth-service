package com.bank.auth_service.exception;

/**
 * Exception thrown when a verification code does not exist or has expired.
 * Used to indicate that the code is either missing or no longer valid.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
public class CodeNotFoundOrExpiredException extends RuntimeException {

    /**
     * Constructs a new CodeNotFoundOrExpiredException with a default error message.
     */
    public CodeNotFoundOrExpiredException() {
        super("O código não existe ou está expirado");
    }
}
