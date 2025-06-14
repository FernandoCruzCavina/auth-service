package com.bank.auth_service.dto;

import java.math.BigDecimal;

public record ConfirmCodeDto(
    String key,
    String code,
    Long idAccount,
    String pixKey,
    Long paymentId,
    String paymentDescription,
    BigDecimal amountPaid
) {}
