package com.bank.auth_service.dto;

public record AuthenticationTokenDto(
    String token,
    String refreshToken
) {
}
