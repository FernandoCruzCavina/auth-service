package com.bank.auth_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.service.VerificationCodeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/code")
public class VerificationCodeController {
    
    private VerificationCodeService verificationCodeService;

    public VerificationCodeController(VerificationCodeService verificationCodeService){
        this.verificationCodeService = verificationCodeService;
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
