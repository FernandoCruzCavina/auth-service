package com.bank.auth_service.exception;

public class RefreshTokenExpiredException extends RuntimeException{

    public RefreshTokenExpiredException(){
        super("this token already have expired");
    }
}
