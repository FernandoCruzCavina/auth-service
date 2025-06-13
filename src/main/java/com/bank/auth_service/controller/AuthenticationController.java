package com.bank.auth_service.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.auth_service.dto.AuthenticationTokenDto;
import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.service.AuthenticationService;
import com.bank.auth_service.service.VerificationCodeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private VerificationCodeService verificationCodeService;

    public AuthenticationController(AuthenticationService authenticationService, VerificationCodeService verificationCodeService) {
        this.authenticationService = authenticationService;
        this.verificationCodeService = verificationCodeService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationTokenDto> authenticate(@RequestBody LoginUserDto loginUserDto, HttpServletRequest httpServletRequest) {
        AuthenticationTokenDto tokens = authenticationService.login(loginUserDto, httpServletRequest);

        return ResponseEntity.status(HttpStatus.OK).body(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody UUID refreshToken, HttpServletRequest httpServletRequest) {
        String newToken = authenticationService.refreshToken(refreshToken, httpServletRequest);
        
        return ResponseEntity.status(HttpStatus.OK).body(newToken);
    }

    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody String userId) {
        String code = verificationCodeService.generateCode(userId);

        return ResponseEntity.status(HttpStatus.OK).body(code);
    }

    @PostMapping("/validate")
    public ResponseEntity<String> validateCode(@RequestBody ConfirmCodeDto confirmCode) {
        String message = verificationCodeService.validateCode(confirmCode);
        
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    
}
