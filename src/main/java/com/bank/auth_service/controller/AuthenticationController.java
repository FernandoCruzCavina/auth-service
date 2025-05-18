package com.bank.auth_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.auth_service.dto.AuthenticationToken;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationToken> authenticate(@RequestBody LoginUserDto loginUserDto) {
        AuthenticationToken tokens = authenticationService.login(loginUserDto);

        return ResponseEntity.status(HttpStatus.OK).body(tokens);
    }
}
