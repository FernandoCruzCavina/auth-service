package com.bank.auth_service.dto;

import java.math.BigDecimal;

public record ConclusionPaymentDto (
    Long idAccount,
    String pixKey,
    String paymentDescription,
    BigDecimal amountPaid
) {}
