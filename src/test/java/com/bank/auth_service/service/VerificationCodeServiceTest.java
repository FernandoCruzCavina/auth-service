package com.bank.auth_service.service;

import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.exception.DontExistOrExpiredCode;
import com.bank.auth_service.exception.InvalidCodeException;
import com.bank.auth_service.model.Code;
import com.bank.auth_service.repository.CodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VerificationCodeServiceTest {

    private CodeRepository codeRepository;
    private VerificationCodeService verificationCodeService;

    @BeforeEach
    void setUp() {
        codeRepository = mock(CodeRepository.class);
        verificationCodeService = new VerificationCodeService(codeRepository);
    }

    @Test
    void generateCode_shouldSaveAndReturnCode() {
        String key = "testKey";
        ArgumentCaptor<Code> codeCaptor = ArgumentCaptor.forClass(Code.class);

        String code = verificationCodeService.generateCode(key);

        verify(codeRepository).save(codeCaptor.capture());
        assertEquals(key, codeCaptor.getValue().getKey());
        assertEquals(code, codeCaptor.getValue().getCode());
        assertNotNull(code);
        assertEquals(6, code.length());
    }

    @Test
    void validateCode_shouldReturnOkWhenCodeIsValid() {
        String key = "testKey";
        String code = "123456";
        long now = Instant.now().toEpochMilli();
        Code codeModel = new Code(key, code, now);

        when(codeRepository.findByKey(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, code);

        String result = verificationCodeService.validateCode(confirmCode);

        verify(codeRepository).delete(codeModel);
        assertEquals("OK! The code is correct!", result);
    }

    @Test
    void validateCode_shouldThrowInvalidCodeExceptionWhenCodeIsInvalid() {
        String key = "testKey";
        String code = "123456";
        long now = Instant.now().toEpochMilli();
        Code codeModel = new Code(key, code, now);

        when(codeRepository.findByKey(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, "wrongCode");

        assertThrows(InvalidCodeException.class, () -> verificationCodeService.validateCode(confirmCode));
        verify(codeRepository, never()).delete(any());
    }

    @Test
    void validateCode_shouldThrowInvalidCodeExceptionWhenCodeIsExpired() {
        String key = "testKey";
        String code = "123456";
        long expiredTime = Instant.now().minusSeconds(3 * 60).toEpochMilli();
        Code codeModel = new Code(key, code, expiredTime);

        when(codeRepository.findByKey(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, code);

        assertThrows(DontExistOrExpiredCode.class, () -> verificationCodeService.validateCode(confirmCode));
        verify(codeRepository, never()).delete(any());
    }
}