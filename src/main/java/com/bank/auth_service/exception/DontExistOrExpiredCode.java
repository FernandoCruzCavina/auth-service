package com.bank.auth_service.exception;

public class DontExistOrExpiredCode extends RuntimeException{
    
    public DontExistOrExpiredCode(){
        super("Don't exist this code or it expired");
    }
}
