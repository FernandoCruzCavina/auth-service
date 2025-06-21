package com.bank.auth_service.service;

import com.bank.auth_service.dto.ConfirmCodeDto;


public interface VerificationCodeService {

    public String generateCode(String key);

    public String validateCode(ConfirmCodeDto confirmCode);
}