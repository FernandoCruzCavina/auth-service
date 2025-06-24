package com.bank.auth_service.service.impl;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.bank.auth_service.dto.ConfirmCodeDto;
import com.bank.auth_service.exception.CodeNotFoundOrExpiredException;
import com.bank.auth_service.exception.InvalidCodeException;
import com.bank.auth_service.model.Code;
import com.bank.auth_service.publish.CodePublisher;
import com.bank.auth_service.repository.CodeRepository;
import com.bank.auth_service.service.VerificationCodeService;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService{

    private final CodeRepository codeRepository;
    private final CodePublisher codePublisher;

    public VerificationCodeServiceImpl(CodeRepository codeRepository, CodePublisher codePublisher){
        this.codeRepository = codeRepository;
        this.codePublisher = codePublisher;
    }

    public String generateCode(String key){
        var code = String.format("%06d", new Random().nextInt(1_000_000));
        var codeModel = new Code(key, code, Instant.now().toEpochMilli());
        
        codeRepository.save(codeModel);
        codePublisher.publishMessageEmailWithCodeSecurity(codeModel);

        return "Código gerado com sucesso! Verifique seu e-mail para confirmar o pagamento.";
    }

    public String validateCode(ConfirmCodeDto confirmCode){
        Optional<List<Code>> storeCode = codeRepository.findByKeyCode(confirmCode.key());
        Long now = Instant.now().toEpochMilli();
        Optional<Code> validCode = storeCode
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
    // private final RedisTemplate<String, String> redisTemplate;

    // public VerificationCodeService(RedisTemplate<String, String> redisTemplate){
    //     this.redisTemplate = redisTemplate;
    // }

    // public String generateCode(String key){

    //     String code = String.format("%06d", new Random().nextInt(1_000_000));

    //     ValueOperations<String, String> ops = redisTemplate.opsForValue();
    //     ops.set(key, code, Duration.ofMinutes(2));

    //     return code;
    // }

    // public String validationCode(String key, String inputCode) throws Exception{

    //     ValueOperations<String, String> ops = redisTemplate.opsForValue();
    //     String storeCode = ops.get(key);

    //     if(inputCode==null) throw new Exception();

    //     boolean isValid = storeCode.equals(inputCode);

    //     if (!isValid) {
    //         throw new Exception();
    //     }
    //     redisTemplate.delete(key);
    //     return "This code " + storeCode + "is correct";
        
    // }
}