package com.bank.auth_service.service.impl;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import com.bank.auth_service.model.UserAuthenticated;
import com.bank.auth_service.service.JwtService;

public class JwtServiceImpl implements JwtService {
    @Value("${jwt.expiration}")
    private long expirationTime;  

    private JwtEncoder jwtEncoder;

    public JwtServiceImpl(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(UserAuthenticated user) {
        List<String> roles = user.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority).toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("auth-service")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expirationTime))
                .subject(user.getUsername())
                .claim("roles", roles)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();   
    }
}
