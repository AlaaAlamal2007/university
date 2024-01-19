package com.example.alaa.university.service;

import com.example.alaa.university.domain.ApplicationUser;
import com.example.alaa.university.dto.JwtAuthenticationResponse;
import com.example.alaa.university.dto.RefreshTokenRequest;
import com.example.alaa.university.dto.SignInRequest;
import com.example.alaa.university.dto.SignUpRequest;

public interface IAuthenticationService {
    ApplicationUser signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signIn(SignInRequest signInRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}


