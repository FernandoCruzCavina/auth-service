package com.bank.auth_service.service;

import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.enums.UserRole;
import com.bank.auth_service.exception.DontExistOrExpiredCode;
import com.bank.auth_service.exception.InvalidCodeException;
import com.bank.auth_service.model.Code;
import com.bank.auth_service.model.User;
import com.bank.auth_service.publish.CodePublisher;
import com.bank.auth_service.repository.CodeRepository;
import com.bank.auth_service.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VerificationCodeServiceTest {

    @Mock
    private CodeRepository codeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CodePublisher codePublisher;
    @InjectMocks
    private VerificationCodeService verificationCodeService;

    private static String key;
    private static String code;

    @BeforeEach
    void setUp() {
        key = "testKey";
        code = "123456";
    }

    @Test
    void generateCode_shouldSaveAndReturnCode() {
        ArgumentCaptor<Code> codeCaptor = ArgumentCaptor.forClass(Code.class);
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User("testKey", "password", UserRole.USER)));

        String code = verificationCodeService.generateCode(key);

        verify(codeRepository).save(codeCaptor.capture());
        assertEquals(key, codeCaptor.getValue().getKey());
        assertEquals(code, codeCaptor.getValue().getCode());
        assertNotNull(code);
        assertEquals(6, code.length());
    }

    @Test
    void validateCode_shouldReturnOkWhenCodeIsValid() {
        long now = Instant.now().toEpochMilli();
        Code codeModel = new Code(key, code, now);

        User mockUser = new User();
        mockUser.setEmail(key);

        when(userRepository.findByEmail(key)).thenReturn(Optional.of(mockUser));
        when(codeRepository.findByKey(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, code);

        String result = verificationCodeService.validateCode(confirmCode);

        verify(codeRepository).delete(codeModel);
        assertEquals("OK! The code is correct!", result);
    }

    @Test
    void validateCode_shouldThrowInvalidCodeExceptionWhenCodeIsInvalid() {
        long now = Instant.now().toEpochMilli();
        Code codeModel = new Code(key, code, now);

        when(codeRepository.findByKey(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, "wrongCode");

        assertThrows(InvalidCodeException.class, () -> verificationCodeService.validateCode(confirmCode));
        verify(codeRepository, never()).delete(any());
    }

    @Test
    void validateCode_shouldThrowInvalidCodeExceptionWhenCodeIsExpired() {
        long expiredTime = Instant.now().minusSeconds(3 * 60).toEpochMilli();
        Code codeModel = new Code(key, code, expiredTime);

        when(codeRepository.findByKey(key)).thenReturn(Optional.of(List.of(codeModel)));

        ConfirmCodeDto confirmCode = new ConfirmCodeDto(key, code);

        assertThrows(DontExistOrExpiredCode.class, () -> verificationCodeService.validateCode(confirmCode));
        verify(codeRepository, never()).delete(any());
    }
}