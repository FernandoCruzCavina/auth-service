package com.bank.auth_service.exception;

public class InvalidUserCredentialsException extends RuntimeException{
    
    public InvalidUserCredentialsException(){
        super("email or password are incorrect");
    }
}
