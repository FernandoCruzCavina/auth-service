package com.bank.auth_service.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.auth_service.dto.AuthenticationToken;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.model.UserAuthenticated;
import com.bank.auth_service.repository.UserRepository;

@Service
public class AuthenticationService {

    @Value("${jwt.expiration}")
    private long expirationTime;
    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationTime;    

    private JwtService jwtService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    AuthenticationService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthenticationToken login(LoginUserDto loginUserDto) {
        var user = userRepository.findByEmail(loginUserDto.email())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        var isPasswordValid = passwordEncoder.matches(loginUserDto.password(), user.getPassword());

        if (!isPasswordValid) {
            throw new RuntimeException("Invalid password");
        }
        var userAuthenticated = new UserAuthenticated(user);

        String token = jwtService.generateToken(userAuthenticated, 54000);
        String refreshToken = jwtService.generateToken(userAuthenticated, 216000);
        
        return new AuthenticationToken(token, refreshToken);
    }
    //email fe@m.com senha fe
}
