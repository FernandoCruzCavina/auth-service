package com.bank.auth_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.auth_service.model.Code;
import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link Code} entities.
 * Provides database access methods for verification codes.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @see Code
 * @since 1.0.0
 */
public interface CodeRepository extends JpaRepository<Code, Long> {

    /**
     * Finds all verification codes associated with the given key.
     *
     * @param key the key (e.g., email) associated with the verification codes
     * @return an optional list of codes, or empty if none found
     */
    Optional<List<Code>> findByKeyCode(String key);
}
