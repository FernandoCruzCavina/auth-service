package com.bank.auth_service.dto;

import java.math.BigDecimal;

/**
 * DTO representing the conclusion of a payment transaction.
 * 
 * <p>
 * Contains the account ID, PIX key, payment description, and the amount paid.
 * </p>
 * 
 * @param idAccount the ID of the account associated with the payment
 * @param pixKey the PIX key used for the payment
 * @param paymentDescription a description of the payment
 * @param amountPaid the total amount paid in the transaction
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
public record ConclusionPaymentDto (
    Long idAccount,
    String pixKey,
    String paymentDescription,
    BigDecimal amountPaid
) {}
