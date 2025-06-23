package com.bank.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuration class for Spring Security.
 * This class defines the security filter chain and password encoder for the application.
 * 
 * @author Fernando Cruz Cavina
 * @version 1.0, 06/23/2025
 * @since 1.0
 */
@Configuration
public class SecurityConfig {

    /**
     * Permits all requests to the /auth/** endpoints and requires authentication for all other requests.
     * In future, this configuration can be extended to include more complex security rules.
     * 
     * @param http the HttpSecurity object used to configure security settings
     * @return a SecurityFilterChain that defines the security rules for the application
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .anyRequest().authenticated()
            );
        return http.build();
    }

    /**
     * Encodes passwords using BCrypt hashing algorithm.
     * This bean is used to hash passwords before storing them in the database.
     * It provides a secure way to handle user passwords by applying a strong hashing algorithm.
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
