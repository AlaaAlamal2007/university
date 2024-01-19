package com.example.alaa.university.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface IJwtService {
    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    public boolean isTokenValid(String token, UserDetails userDetails);
}

