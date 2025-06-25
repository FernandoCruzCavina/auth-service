package com.bank.auth_service.dto;

import java.math.BigDecimal;

/**
 * DTO representing a confirmation code for a payment transaction.
 * 
 * <p>
 * Contains the key, code, account ID, PIX key, payment ID, payment description, and the amount paid.
 * </p>
 * 
 * @param key the unique key for the confirmation
 * @param code the confirmation code
 * @param idAccount the ID of the account associated with the payment
 * @param pixKey the PIX key used for the payment
 * @param paymentId the ID of the payment transaction
 * @param paymentDescription a description of the payment
 * @param amountPaid the total amount paid in the transaction
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
public record ConfirmCodeDto(
    String key,
    String code,
    Long idAccount,
    String pixKey,
    Long paymentId,
    String paymentDescription,
    BigDecimal amountPaid
) {}
