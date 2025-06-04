package com.bank.auth_service.dto;

import com.bank.auth_service.enums.UserRole;

public record AuthUserDto(
    String email,
    String password,
    UserRole userRole
) {}