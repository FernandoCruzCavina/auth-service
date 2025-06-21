package com.bank.auth_service.service.impl;

import java.time.Instant;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.auth_service.dto.AuthenticationTokenDto;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.exception.InvalidUserCredentialsException;
import com.bank.auth_service.exception.UserNotFoundException;
import com.bank.auth_service.model.UserAuthenticated;
import com.bank.auth_service.publish.CodePublisher;
import com.bank.auth_service.repository.UserRepository;
import com.bank.auth_service.service.JwtService;
import com.bank.auth_service.service.RefreshTokenService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationServiceImpl {
    
    private CodePublisher codePublisher;
    private JwtService jwtService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;

    public AuthenticationServiceImpl(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService, CodePublisher codePublisher) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
        this.codePublisher = codePublisher;
    }

    public AuthenticationTokenDto login(LoginUserDto loginUserDto, HttpServletRequest request) {
        var user = userRepository.findByEmail(loginUserDto.email())
            .orElseThrow(UserNotFoundException::new);
        
            System.out.println(user.getEmail());
        var isPasswordValid = passwordEncoder.matches(loginUserDto.password(), user.getPassword());
        if (!isPasswordValid) {
            throw new InvalidUserCredentialsException();
        }
        System.out.println(isPasswordValid);
        var userAuthenticated = new UserAuthenticated(user);
        var ip = getIpAddress(request);
        var userAgent = request.getHeader("User-Agent");

        String token = jwtService.generateToken(userAuthenticated);
        String refreshToken = refreshTokenService.createRefreshToken(ip, userAgent, user);
        
        return new AuthenticationTokenDto(token, refreshToken);
    }
    
    public String refreshToken(UUID refreshToken, HttpServletRequest request){
        var tokenValidated = refreshTokenService.validateRefreshToken(refreshToken);
        
        var ip = getIpAddress(request);
        var userAgent = request.getHeader("User-Agent");

        var ipDatabase = tokenValidated.getIp();
        var userAgentDatabase = tokenValidated.getUserAgent();
        
        if(!ipDatabase.equals(ip) && !userAgentDatabase.equals(userAgent)){
            codePublisher.publishMessageEmailWithUnusualAccessWarning(tokenValidated.getUser().getEmail(),ip,userAgent,Instant.now());
        }
        var userAuthenticated = new UserAuthenticated(tokenValidated.getUser());
        var newToken = jwtService.generateToken(userAuthenticated);

        return newToken;
    }

    public String getIpAddress(HttpServletRequest request) {
    
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isEmpty()) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}