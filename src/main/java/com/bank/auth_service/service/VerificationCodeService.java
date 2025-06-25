package com.bank.auth_service.service;

import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.exception.CodeNotFoundOrExpiredException;
import com.bank.auth_service.exception.InvalidCodeException;
import com.bank.auth_service.publish.CodePublisher;
import com.bank.auth_service.repository.CodeRepository;

/**
 * Service interface for handling verification code operations.
 * Provides methods to generate and validate temporary verification codes.
 * <p>
 * Actually used for sensitive actions such as confim payments or password resets.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @see CodeRepository
 * @since 1.0.0
 */
public interface VerificationCodeService {

    /**
     * Generates a 6-digit numeric verification code tied to a unique key.
     * The key may represent an email address, phone number, or another identifier.
     *
     * @param key the unique identifier (e.g., email, phone number)
     * @return a message that says that the code was created and that it's in the email
     * @see CodePublisher#publishMessageEmailWithCodeSecurity(com.bank.auth_service.model.Code)
     */
    String generateCode(String key);

    /**
     * Validates a verification code by checking its existence, expiration, and correctness.
     * Codes are typically valid for 5 minutes after generation.
     *
     * @param confirmCode the DTO containing the code and its associated key
     * @return a confirmation message if the code is valid
     * @throws CodeNotFoundOrExpiredException if the code doesn't exist or has expired
     * @throws InvalidCodeException if the code is incorrect for the given key
     * @see CodePublisher#publishMessageEmailWithUnusualAccessWarning(String, String, String, java.time.Instant)
     * @see CodePublisher#publishValidatePayment(ConfirmCodeDto)
     */
    String validateCode(ConfirmCodeDto confirmCode);
}