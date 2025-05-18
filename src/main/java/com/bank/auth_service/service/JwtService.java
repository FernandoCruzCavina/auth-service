package com.bank.auth_service.service;

import java.time.Instant;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.bank.auth_service.model.UserAuthenticated;

@Service
public class JwtService {

    private JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(UserAuthenticated user, long expirationTime) {
        List<String> scopes = user.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority).toList();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("auth-service")
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(expirationTime))
                .subject(user.getUsername())
                .claim("roles", scopes)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();   
    }

}
