package com.bank.auth_service.dto;

public record ConfirmCodeDto(
    String key,
    String code
) {}
