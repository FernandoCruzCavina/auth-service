package com.bank.auth_service.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.auth_service.dto.AuthenticationTokenDto;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.service.AuthenticationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
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
    
}
