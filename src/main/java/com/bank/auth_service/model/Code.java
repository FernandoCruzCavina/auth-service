package com.bank.auth_service.model;

import jakarta.annotation.Generated;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "code")
public class Code {
    
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String keyCode;
    private String code;
    private Long createdAt;

    public Code(){}

    public Code(String keyCode, String code, Long createdAt){
        this.keyCode = keyCode;
        this.code = code;
        this.createdAt = createdAt;
    }
}
