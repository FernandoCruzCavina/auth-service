package com.bank.auth_service.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.service.VerificationCodeService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller for handling verification code operations.
 * Provides endpoints for generating and validating verification codes.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @see VerificationCodeService
 * @since 1.0
 */
@RestController
@RequestMapping("/code")
public class VerificationCodeController {
    
    private VerificationCodeService verificationCodeService;

    public VerificationCodeController(VerificationCodeService verificationCodeService){
        this.verificationCodeService = verificationCodeService;
    }

    /**
     * Generates a 6-digit verification code for the provided key.
     * The code is sent to the user's email.
     * 
     * @param key the unique key for which the verification code is generated
     * @return a message that says that the code was created and that it's in the email
     */
    @PostMapping("/generate")
    public ResponseEntity<String> generate(@RequestBody String key) {
        String code = verificationCodeService.generateCode(key);

        return ResponseEntity.status(HttpStatus.OK).body(code);
    }

    /**
     * Validates the given verification code against the provided key.
     * 
     * @param confirmCode the DTO containing the code and its associated key
     * @return a confirmation message if the code is valid
     */
    @PostMapping("/validate")
    public ResponseEntity<String> validateCode(@RequestBody ConfirmCodeDto confirmCode) {
        String message = verificationCodeService.validateCode(confirmCode);
        
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    
    

}
