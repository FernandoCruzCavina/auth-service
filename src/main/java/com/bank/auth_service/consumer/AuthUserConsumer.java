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

/**
 * Consumer for user-related messages from the message broker.
 * This class listens for messages related to user creation and code generation.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0.0, 06/23/2025
 * @since 1.0.0
 */
@Component
public class AuthUserConsumer {
    
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    UserRepository userRepository;
    @Autowired
    VerificationCodeService verificationCodeService;

    /**
     * Receives a request to create a user from user microservice.
     * 
     * @param authUserDto
     */
    @RabbitListener(queues = "${broker.queue.create.auth}")
    public void listenerAuthUser(@Payload AuthUserDto authUserDto){
        var user = new User(
            authUserDto.email(),
            authUserDto.password(),
            UserRole.USER
        );

        userRepository.save(user);
    }

    /**
     * Receives a request to generate a code of 6-digit to authorizate a payment microservice make a security payment
     * 
     * @param email
     */
    @RabbitListener(queues = "${broker.queue.requestNewCode}")
    public void generateCode(@Payload String email) {
        verificationCodeService.generateCode(email);
    }
}

