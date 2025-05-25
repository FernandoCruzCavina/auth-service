package com.bank.auth_service.service;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bank.auth_service.dto.AuthenticationToken;
import com.bank.auth_service.dto.LoginUserDto;
import com.bank.auth_service.model.UserAuthenticated;
import com.bank.auth_service.repository.RefreshTokenRepository;
import com.bank.auth_service.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class AuthenticationService {

    private JwtService jwtService;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RefreshTokenService refreshTokenService;

    AuthenticationService(JwtService jwtService, UserRepository userRepository, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    public AuthenticationToken login(LoginUserDto loginUserDto, HttpServletRequest request) {
        var user = userRepository.findByEmail(loginUserDto.email())
            .orElseThrow(() -> new RuntimeException("User not found"));
        
        var isPasswordValid = passwordEncoder.matches(loginUserDto.password(), user.getPassword());

        if (!isPasswordValid) {
            throw new RuntimeException("Invalid password");
        }
        var userAuthenticated = new UserAuthenticated(user);
        var ip = getIpAddress(request);
        var userAgent = request.getHeader("User-Agent");

        String token = jwtService.generateToken(userAuthenticated);
        String refreshToken = refreshTokenService.createRefreshToken(ip, userAgent, user);
        
        return new AuthenticationToken(token, refreshToken);
    }
    
    public String refreshToken(UUID refreshToken, HttpServletRequest request){
        var tokenValidated = refreshTokenService.validateRefreshToken(refreshToken);
        
        var ip = getIpAddress(request);
        var userAgent = request.getHeader("User-Agent");

        var ipDatabase = tokenValidated.getIp();
        var userAgentDatabase = tokenValidated.getUserAgent();
        
        if(!ipDatabase.equals(ip) && !userAgentDatabase.equals(userAgent)){
            //email service active and warning this probabily danger
        }
        var userAuthenticated = new UserAuthenticated(tokenValidated.getUser());
        var newToken = jwtService.generateToken(userAuthenticated);

        return newToken;
    }

    public static String getIpAddress(HttpServletRequest request){
        String xfHeader = request.getHeader("X-Forwarded_For");
        
        if(!xfHeader.isEmpty()){
            return xfHeader.split(",")[0];
        }

        return request.getRemoteAddr();
    }
}
