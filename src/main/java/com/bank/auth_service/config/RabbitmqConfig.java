package com.bank.auth_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RabbitmqConfig {

    @Value("${broker.queue.create.auth}")
    public String authQueue;

    @Value("${broker.queue.email.sender}")
    public String emailQueue; 

    @Value("${broker.queue.requestNewCode}")
    public String requestNewCodeQueue;

    @Value("${broker.queue.sendPayment}")
    public String sendPaymentQueue;

    @Bean
    public Queue authQueue() {
        return new Queue(authQueue, true);
    }

    @Bean
    public Queue emailQueue(){
        return new Queue(emailQueue, true);
    }

    @Bean
    public Queue requestNewCodeQueue() {
        return new Queue(requestNewCodeQueue, true);
    }

    @Bean
    public Queue sendPaymentQueue() {
        return new Queue(sendPaymentQueue, true);
    }

    @Bean
    public Jackson2JsonMessageConverter objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
