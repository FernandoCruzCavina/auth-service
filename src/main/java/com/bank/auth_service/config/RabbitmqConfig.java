package com.bank.auth_service.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configuration class for RabbitMQ queues and message conversion.
 * <p>
 * This class defines the beans for the application's RabbitMQ queues and configures
 * the message converter to use Jackson for JSON serialization and deserialization.
 * The queue names are injected from application properties.
 * </p>
 * 
 * <ul>
 *   <li>{@code authQueue}: Queue for authentication-related messages</li>
 *   <li>{@code emailQueue}: Queue for sending email notifications</li>
 *   <li>{@code requestNewCodeQueue}: Queue for requesting new verification codes</li>
 *   <li>{@code sendPaymentQueue}: Queue for sending payment-related messages</li>
 * </ul>
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
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

    /**
     * Defines the authentication queue bean.
     * 
     * @return a durable Queue for authentication messages
     */
    @Bean
    public Queue authQueue() {
        return new Queue(authQueue, true);
    }

    /**
     * Defines the email queue bean.
     * 
     * @return a durable Queue for email messages
     */
    @Bean
    public Queue emailQueue(){
        return new Queue(emailQueue, true);
    }

    /**
     * Defines the request new code queue bean.
     * 
     * @return a durable Queue for code request messages
     */
    @Bean
    public Queue requestNewCodeQueue() {
        return new Queue(requestNewCodeQueue, true);
    }

    /**
     * Defines the send payment queue bean.
     * 
     * @return a durable Queue for payment messages
     */
    @Bean
    public Queue sendPaymentQueue() {
        return new Queue(sendPaymentQueue, true);
    }

    /**
     * Configures the Jackson2JsonMessageConverter bean for message serialization.
     * 
     * @return a Jackson2JsonMessageConverter using a custom ObjectMapper
     */
    @Bean
    public Jackson2JsonMessageConverter objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
