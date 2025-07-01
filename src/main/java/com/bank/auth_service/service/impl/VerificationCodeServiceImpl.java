package com.bank.auth_service.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.exception.CodeNotFoundOrExpiredException;
import com.bank.auth_service.exception.InvalidCodeException;
import com.bank.auth_service.model.VerificationCode;
import com.bank.auth_service.publish.CodePublisher;
import com.bank.auth_service.repository.CodeRepository;
import com.bank.auth_service.service.VerificationCodeService;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService{

    private final CodeRepository codeRepository;
    private final CodePublisher codePublisher;
    private Random randomCode = new Random();

    public VerificationCodeServiceImpl(CodeRepository codeRepository, CodePublisher codePublisher){
        this.codeRepository = codeRepository;
        this.codePublisher = codePublisher;
    }

    public String generateCode(String key){
        var code = String.format("%06d", randomCode.nextInt(1_000_000));
        var codeModel = new VerificationCode(key, code, Instant.now().toEpochMilli());
        
        codeRepository.save(codeModel);
        codePublisher.publishMessageEmailWithCodeSecurity(codeModel);

        return "Código gerado com sucesso! Verifique seu e-mail para confirmar o pagamento.";
    }

    public String validateCode(ConfirmCodeDto confirmCode){
        Optional<List<VerificationCode>> storeCode = codeRepository.findByKeyCode(confirmCode.key());
        Long now = Instant.now().toEpochMilli();
        Optional<VerificationCode> validCode = storeCode
            .flatMap(codes -> codes.stream()
                .filter(code -> now - code.getCreatedAt() <= 5 * 60 * 1000)
                .findFirst());
                
        if(validCode.isEmpty()){
            throw new CodeNotFoundOrExpiredException();
        }

        if(!validCode.get().getCode().equals(confirmCode.code())){
            throw new InvalidCodeException();
        }

        codeRepository.delete(validCode.get());
        codePublisher.publishValidatePayment(confirmCode);  
        return "OK! seu codigo e valido: " + validCode.get().getCode() + " seu pagamento foi realizado com sucesso!";
    }
    
}