package com.bank.auth_service.publish;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bank.auth_service.dto.SendEmailDto;
import com.bank.auth_service.model.Code;
import com.bank.auth_service.model.User;

@Component
public class CodePublisher {
    
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${broker.queue.email.sender}")
    private String routingEmailKey;

    public void publishMessageEmailWithCodeSecurity(Code code){
        var emailDto = new SendEmailDto();
        
        emailDto.setEmailTo(code.getKey());
        emailDto.setSubject("Confirmation with Security Code");
        emailDto.setText("Hello,\n\n"
            + "You have requested a security code. Here is your code:\n\n"
            + code.getCode() + "\n\n"
            + "This code is valid for 2 minutes from the time it was issued in " + code.getCreatedAt()+ " .\n" 
            + "If you did not request this code, please ignore this email.\n\n"
            + "Best regards,\n"
            + "Your favorite Bank");

        rabbitTemplate.convertAndSend("", routingEmailKey,emailDto);
    }

    public void publishValidatePayment(String response){
        rabbitTemplate.convertAndSend("", response);
    }
}
