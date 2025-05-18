package com.bank.auth_service.dto;

public record AuthenticationToken(
    String token,
    String refreshToken
) {
}
