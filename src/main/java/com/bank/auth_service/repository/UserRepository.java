package com.bank.auth_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.auth_service.model.User;


/**
 * Repository interface for accessing {@link User} entities.
 * Provides methods for retrieving user data from the database.
 *
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @see User
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, String> {
    /**
     * Finds a user by their email address.
     *
     * @param email the email to search for
     * @return an {@link Optional} containing the user, if found
     */
    Optional<User> findByEmail(String email);
}
