package com.bank.auth_service.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    
    public RefreshTokenNotFoundException(){
        super("not have this refreshToken in this service");
    }
}
