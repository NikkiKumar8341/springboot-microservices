package com.example.security_basic.service;


import com.example.security_basic.entity.RefreshToken;
import com.example.security_basic.entity.UserRegister;
import com.example.security_basic.repo.RefreshTokenRepo;
import com.example.security_basic.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepo refreshTokenRepo;

    @Autowired
    private UserRepo userRepo;

    public RefreshToken createRefreshToken(String username) {
        Optional<UserRegister> userInfoExtracted = userRepo.findByEmailId(username);

        RefreshToken refreshToken = RefreshToken.builder()
                .userRegister(userInfoExtracted.get())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plusMillis(60000)) // Set expiry for refresh token, adjust as needed
                .build();

        return refreshTokenRepo.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new RuntimeException(token.getToken() + " refresh token is expired. Please make a new login.");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

}
