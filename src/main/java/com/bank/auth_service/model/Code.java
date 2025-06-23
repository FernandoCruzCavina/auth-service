package com.bank.auth_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entity representing a verification code used for user validation processes.
 * Stores the code, its key (e.g., email), and the creation timestamp.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
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

    /**
     * Default constructor.
     */
    public Code(){}

    /**
     * Constructs a new Code with the specified key, code, and creation time.
     *
     * @param keyCode the key associated with the code (e.g., email)
     * @param code the verification code
     * @param createdAt the creation timestamp
     */
    public Code(String keyCode, String code, Long createdAt){
        this.keyCode = keyCode;
        this.code = code;
        this.createdAt = createdAt;
    }
}
