package com.bank.auth_service.dto;

/**
 * DTO representing a user login request.
 * <p>
 * Contains the user's email and password for authentication.
 * 
 * @param email the user's email address
 * @param password the user's password
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
public record LoginUserDto(
    String email,
    String password
) {}
