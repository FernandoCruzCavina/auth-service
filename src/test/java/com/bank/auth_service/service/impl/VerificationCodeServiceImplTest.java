package com.bank.auth_service.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.exception.CodeNotFoundOrExpiredException;
import com.bank.auth_service.exception.InvalidCodeException;
import com.bank.auth_service.model.Code;
import com.bank.auth_service.publish.CodePublisher;
import com.bank.auth_service.repository.CodeRepository;

@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceImplTest {

    @Mock
    private CodeRepository codeRepository;
    @Mock
    private CodePublisher codePublisher;
    @InjectMocks
    private VerificationCodeServiceImpl verificationCodeService;

    private String key;
    private String code;

    @BeforeEach
    void setUp() {
        key = "testKey";
        code = "123456";
    }

    @Test
    void generateCode_shouldSaveAndReturnCodeAndPublish() {
        ArgumentCaptor<Code> codeCaptor = ArgumentCaptor.forClass(Code.class);

        String generatedCode = verificationCodeService.generateCode(key);

        verify(codeRepository).save(codeCaptor.capture());
        verify(codePublisher).publishMessageEmailWithCodeSecurity(codeCaptor.getValue());
        assertEquals(key, codeCaptor.getValue().getKeyCode());
        assertEquals(generatedCode, "Código gerado com sucesso! Verifique seu e-mail para confirmar o pagamento.");
        assertNotNull(generatedCode);
    }

    @Test
    void validateCode_shouldReturnOkWhenCodeIsValid() {
        long now = Instant.now().toEpochMilli();
        Code codeModel = new Code(key, code, now);

        when(codeRepository.findByKeyCode(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, code, 1L, "pix", 2L, "desc", null);

        String result = verificationCodeService.validateCode(confirmCode);

        verify(codeRepository).delete(codeModel);
        verify(codePublisher).publishValidatePayment(confirmCode);
        assertEquals("OK! seu codigo e valido: " + code + " seu pagamento foi realizado com sucesso!", result);
    }

    @Test
    void validateCode_shouldThrowInvalidCodeExceptionWhenCodeIsInvalid() {
        long now = Instant.now().toEpochMilli();
        Code codeModel = new Code(key, code, now);

        when(codeRepository.findByKeyCode(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, "wrongCode", 1L, "pix", 2L, "desc", null);

        assertThrows(InvalidCodeException.class, () -> verificationCodeService.validateCode(confirmCode));
        verify(codeRepository, never()).delete(any());
        verify(codePublisher, never()).publishValidatePayment(any());
    }

    @Test
    void validateCode_shouldThrowCodeNotFoundOrExpiredWhenCodeIsExpired() {
        long expiredTime = Instant.now().minusSeconds(5 * 60).toEpochMilli();
        Code codeModel = new Code(key, code, expiredTime);

        when(codeRepository.findByKeyCode(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, code, 1L, "pix", 2L, "desc", null);

        assertThrows(CodeNotFoundOrExpiredException.class, () -> verificationCodeService.validateCode(confirmCode));
        verify(codeRepository, never()).delete(any());
        verify(codePublisher, never()).publishValidatePayment(any());
    }

    @Test
    void validateCode_shouldThrowCodeNotFoundOrExpiredWhenNoCodeFound() {
        when(codeRepository.findByKeyCode(key)).thenReturn(Optional.empty());

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, code, 1L, "pix", 2L, "desc", null);

        assertThrows(CodeNotFoundOrExpiredException.class, () -> verificationCodeService.validateCode(confirmCode));
        verify(codeRepository, never()).delete(any());
        verify(codePublisher, never()).publishValidatePayment(any());
    }
}