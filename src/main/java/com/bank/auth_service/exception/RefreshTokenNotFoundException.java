package com.bank.auth_service.exception;

public class RefreshTokenNotFoundException extends RuntimeException {
    
    public RefreshTokenNotFoundException(){
        super("Não tem esse refreshToken nesse serviço");
    }
}
