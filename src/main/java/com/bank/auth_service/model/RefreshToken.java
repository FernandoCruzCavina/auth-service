package com.bank.auth_service.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "refresh_token", schema = "auth_service")
public class RefreshToken {

    @Id
    private UUID refreshToken;
    private long expirationDate;
    private String ip;
    private String userAgent;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    public RefreshToken(long expirationDate, String ip, String userAgent, User user) {
        this.expirationDate = expirationDate;
        this.ip = ip;
        this.userAgent = userAgent;
        this.user = user;
    }
}
