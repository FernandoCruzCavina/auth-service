package com.bank.auth_service.exception;

public class DontExistOrExpiredCode extends RuntimeException{
    
    public DontExistOrExpiredCode(){
        super("Não existe esse código ou ele expirou");
    }
}
