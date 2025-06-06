package com.bank.auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "Code")
public class Code {
    
    @Id
    private Long id;
    private String key;
    private String code;
    private Long createdAt;

    public Code(String key, String code, Long createdAt){
        this.key = key;
        this.code = code;
        this.createdAt = createdAt;
    }
}
