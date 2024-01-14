package com.example.alaa.university.controller;

import com.example.alaa.university.domain.ApplicationUser;
import com.example.alaa.university.dto.JwtAuthenticationResponse;
import com.example.alaa.university.dto.RefreshTokenRequest;
import com.example.alaa.university.dto.SignInRequest;
import com.example.alaa.university.dto.SignUpRequest;
import com.example.alaa.university.service.IAuthenticationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final IAuthenticationService iAuthenticationService;

    public AuthenticationController(IAuthenticationService iAuthenticationService) {
        this.iAuthenticationService = iAuthenticationService;
    }

    @PostMapping("/signup")
    public ApplicationUser signup(@RequestBody SignUpRequest signUpRequest) {
        return iAuthenticationService.signUp(signUpRequest);
    }

    @PostMapping("/signin")
    public @ResponseBody JwtAuthenticationResponse signIn(@RequestBody SignInRequest signRequest) {
        return iAuthenticationService.signIn(signRequest);
    }

    @PostMapping("/refresh")
    public @ResponseBody JwtAuthenticationResponse refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return iAuthenticationService.refreshToken(refreshTokenRequest);
    }
}


