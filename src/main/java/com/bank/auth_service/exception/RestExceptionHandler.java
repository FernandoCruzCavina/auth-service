package com.bank.auth_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {


    @ExceptionHandler(InvalidUserCredentialsException.class)
    public ResponseEntity<MessageHandler> handlerCredentialsLogin(InvalidUserCredentialsException ex){

        MessageHandler messageException = new MessageHandler(HttpStatus.BAD_REQUEST, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageException);
    }

    @ExceptionHandler(InvalidCodeException.class)
    public ResponseEntity<MessageHandler> handlerInvalidCode(InvalidCodeException ex){

        MessageHandler messageException = new MessageHandler(HttpStatus.BAD_REQUEST, ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageException);
    }

    @ExceptionHandler(DontExistOrExpiredCode.class)
    public ResponseEntity<MessageHandler> handlerInvalidCode(DontExistOrExpiredCode ex){

        MessageHandler messageException = new MessageHandler(HttpStatus.BAD_REQUEST, ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageException);
    }

}