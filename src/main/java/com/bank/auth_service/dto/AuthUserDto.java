package com.bank.auth_service.dto;

import com.bank.auth_service.enums.UserRole;

/**
 * DTO representing a user model used for creating or updating user accounts.
 * 
 * <p>
 * Contains the user's email, password, and role.
 * </p>
 * 
 * @param email the user's email address
 * @param password the user's password
 * @param userRole the role of the user (e.g., ADMIN, USER)
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
public record AuthUserDto(
    String email,
    String password,
    UserRole userRole
) {}