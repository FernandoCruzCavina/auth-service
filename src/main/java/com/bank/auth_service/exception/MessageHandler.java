package com.bank.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Handler class for error messages returned in API responses.
 * Encapsulates the HTTP status and error message.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
public class MessageHandler extends ResponseEntityExceptionHandler  {
    
    private HttpStatus status;
    private String message;
}
