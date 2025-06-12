package com.bank.auth_service.exception;

public class RefreshTokenExpiredException extends RuntimeException{

    public RefreshTokenExpiredException(){
        super("Esse token já foi expirado");
    }
}
