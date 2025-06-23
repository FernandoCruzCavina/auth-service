package com.bank.auth_service.service;

import com.bank.auth_service.config.KeyConfig;
import com.bank.auth_service.model.UserAuthenticated;

/**
 * Service interface for handling JSON Web Token (JWT) operations.
 * Provides functionality for generating JWTs for authenticated users.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @see UserAuthenticated
 * @see KeyConfig
 * @see <a href="https://jwt.io/">JWT.io</a>
 * @since 1.0
 */
public interface JwtService {

    /**
     * Generates a signed JWT for the specified authenticated user.
     * The token is used to authorize access to protected resources in other microservices.
     *
     * @param user the authenticated user
     * @return a signed JWT token as a String
     */
    String generateToken(UserAuthenticated user);
}