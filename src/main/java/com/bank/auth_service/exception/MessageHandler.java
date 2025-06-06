package com.bank.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MessageHandler extends ResponseEntityExceptionHandler  {
    
    private HttpStatus status;
    private String message;
}
