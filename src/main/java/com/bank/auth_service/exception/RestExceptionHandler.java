package com.bank.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for the authentication service.
 * Handles custom exceptions and returns appropriate HTTP responses with error messages.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
@ControllerAdvice
public class RestExceptionHandler {

    /**
     * Handles InvalidUserCredentialsException and returns a 400 BAD REQUEST response.
     *
     * @param ex the thrown InvalidUserCredentialsException
     * @return a ResponseEntity containing a MessageHandler with BAD_REQUEST status and error message
     */
    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<MessageHandler> handlerCredentialsLogin(InvalidUserCredentialsException ex){
        MessageHandler messageException = new MessageHandler(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageException);
    }

    /**
     * Handles InvalidCodeException and returns a 400 BAD REQUEST response.
     *
     * @param ex the thrown InvalidCodeException
     * @return a ResponseEntity containing a MessageHandler with BAD_REQUEST status and error message
     */
    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<MessageHandler> handlerInvalidCode(InvalidCodeException ex){
        MessageHandler messageException = new MessageHandler(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageException);
    }

    /**
     * Handles CodeNotFoundOrExpiredException and returns a 400 BAD REQUEST response.
     *
     * @param ex the thrown CodeNotFoundOrExpiredException
     * @return a ResponseEntity containing a MessageHandler with BAD_REQUEST status and error message
     */
    @ExceptionHandler(CodeNotFoundOrExpiredException.class)
    public ResponseEntity<MessageHandler> handlerDontExistOrExpiratedCode(CodeNotFoundOrExpiredException ex){
        MessageHandler messageException = new MessageHandler(HttpStatus.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageException);
    }

    /**
     * Handles UserNotFoundException and returns a 404 NOT FOUND response.
     *
     * @param ex the thrown UserNotFoundException
     * @return a ResponseEntity containing a MessageHandler with NOT_FOUND status and error message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<MessageHandler> handlerUserNotFound(UserNotFoundException ex){
        MessageHandler messageException = new MessageHandler(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    /**
     * Handles RefreshTokenNotFoundException and returns a 404 NOT FOUND response.
     *
     * @param ex the thrown RefreshTokenNotFoundException
     * @return a ResponseEntity containing a MessageHandler with NOT_FOUND status and error message
     */
    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<MessageHandler> handlerRefreshTokenNotFound(RefreshTokenNotFoundException ex){
        MessageHandler messageException = new MessageHandler(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

    /**
     * Handles RefreshTokenExpiredException and returns a 404 NOT FOUND response.
     *
     * @param ex the thrown RefreshTokenExpiredException
     * @return a ResponseEntity containing a MessageHandler with NOT_FOUND status and error message
     */
    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<MessageHandler> handlerRefreshTokenExpired(RefreshTokenExpiredException ex){
        MessageHandler messageException = new MessageHandler(HttpStatus.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }
}