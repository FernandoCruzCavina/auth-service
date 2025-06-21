package com.bank.auth_service.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.bank.auth_service.dto.AuthUserDto;
import com.bank.auth_service.enums.UserRole;
import com.bank.auth_service.model.User;
import com.bank.auth_service.repository.UserRepository;
import com.bank.auth_service.service.VerificationCodeService;

@Component
public class AuthUserConsumer {
    
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationCodeService verificationCodeService;

    @RabbitListener(queues = "${broker.queue.create.auth}")
    public void listenerAuthUser(@Payload AuthUserDto authUserDto){
        var user = new User(
            authUserDto.email(),
            authUserDto.password(),
            UserRole.USER
        );

        userRepository.save(user);
    }

    @RabbitListener(queues = "${broker.queue.requestNewCode}")
    public void generateCode(@Payload String email) {
        verificationCodeService.generateCode(email);
    }
}

