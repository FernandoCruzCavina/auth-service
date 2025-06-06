package com.bank.auth_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bank.auth_service.dto.AuthenticationTokenDto;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.exception.InvalidUserCredentialsException;
import com.bank.auth_service.exception.UserNotFoundException;
import com.bank.auth_service.model.User;
import com.bank.auth_service.model.UserAuthenticated;
import com.bank.auth_service.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    private JwtService jwtService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        jwtService = mock(JwtService.class);
        userRepository = mock(UserRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        refreshTokenService = mock(RefreshTokenService.class);
        authenticationService = new AuthenticationService(jwtService, userRepository, passwordEncoder, refreshTokenService);
    }

    @Test
    void testLogin_success() {
        LoginUserDto loginUserDto = new LoginUserDto("user@email.com", "password");
        User user = mock(User.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(userRepository.findByEmail(loginUserDto.email())).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn(loginUserDto.email());
        when(user.getPassword()).thenReturn("hashedPassword");
        when(passwordEncoder.matches(loginUserDto.password(), "hashedPassword")).thenReturn(true);
        when(jwtService.generateToken(any(UserAuthenticated.class))).thenReturn("jwt-token");
        when(refreshTokenService.createRefreshToken(any(), any(), eq(user))).thenReturn("refresh-token");
        when(request.getHeader("User-Agent")).thenReturn("JUnit");
        when(request.getHeader("X-Forwarded-For")).thenReturn(null);
        when(request.getRemoteAddr()).thenReturn("127.0.0.1");

        AuthenticationTokenDto token = authenticationService.login(loginUserDto, request);

        assertEquals("jwt-token", token.token());
        assertEquals("refresh-token", token.refreshToken());
    }

    @Test
    void testLogin_shouldThrowUserNotFoundException() {
        LoginUserDto loginUserDto = new LoginUserDto("notfound@email.com", "password");
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(userRepository.findByEmail(loginUserDto.email())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> authenticationService.login(loginUserDto, request));
    }

    @Test
    void testLogin_shouldThrowInvalidUserCredentialsException() {
        LoginUserDto loginUserDto = new LoginUserDto("user@email.com", "wrongpassword");
        User user = mock(User.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(userRepository.findByEmail(loginUserDto.email())).thenReturn(Optional.of(user));
        when(user.getEmail()).thenReturn(loginUserDto.email());
        when(user.getPassword()).thenReturn("hashedPassword");
        when(passwordEncoder.matches(loginUserDto.password(), "hashedPassword")).thenReturn(false);

        assertThrows(InvalidUserCredentialsException.class, () -> authenticationService.login(loginUserDto, request));
    }
}