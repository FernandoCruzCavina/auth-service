package com.bank.auth_service.exception;

public class InvalidCodeException extends RuntimeException {
    
    public InvalidCodeException(){
        super("Esse código está incorreto");
    }
}
