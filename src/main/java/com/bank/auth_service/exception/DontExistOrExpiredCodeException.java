package com.bank.auth_service.exception;

public class DontExistOrExpiredCodeException extends RuntimeException{
    
    public DontExistOrExpiredCodeException(){
        super("Não existe esse código ou ele expirou");
    }
}
