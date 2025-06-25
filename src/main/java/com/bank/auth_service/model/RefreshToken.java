package com.bank.auth_service.model;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Entity representing a refresh token for user authentication.
 * Stores the token, expiration date, client information, and associated user.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "refresh_token_tb")
public class RefreshToken {

    @Id
    private UUID refreshToken;
    private long expirationDate;
    private String ip;
    private String userAgent;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * Constructs a new RefreshToken with the specified expiration, client info, and user.
     *
     * @param expirationDate the expiration timestamp
     * @param ip the IP address of the client
     * @param userAgent the user agent string of the client
     * @param user the associated user
     */
    public RefreshToken(long expirationDate, String ip, String userAgent, User user) {
        this.expirationDate = expirationDate;
        this.ip = ip;
        this.userAgent = userAgent;
        this.user = user;
    }
}
