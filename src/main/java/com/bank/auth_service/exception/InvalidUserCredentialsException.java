package com.bank.auth_service.exception;

public class InvalidUserCredentialsException extends RuntimeException{
    
    public InvalidUserCredentialsException(){
        super("Email ou a senha está incorreto");
    }
}
