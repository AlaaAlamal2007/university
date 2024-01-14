package com.example.alaa.university.service;

import com.example.alaa.university.domain.ApplicationUser;
import com.example.alaa.university.domain.Role;
import com.example.alaa.university.dto.JwtAuthenticationResponse;
import com.example.alaa.university.dto.RefreshTokenRequest;
import com.example.alaa.university.dto.SignInRequest;
import com.example.alaa.university.dto.SignUpRequest;
import com.example.alaa.university.repository.ApplicationUserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final ApplicationUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IJwtService iJwtService;

    public AuthenticationService(ApplicationUserRepo appUserRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, IJwtService iJwtService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.iJwtService = iJwtService;
    }

    public ApplicationUser signUp(SignUpRequest signUpRequest) {
        ApplicationUser appUser = new ApplicationUser();
        appUser.setEmail(signUpRequest.getEmail());
        appUser.setFirstName(signUpRequest.getFirstName());
        appUser.setLastName(signUpRequest.getLastName());
        appUser.setRole(Role.DEFAULT);
        appUser.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return appUserRepo.save(appUser);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        ApplicationUser user = appUserRepo.findByEmail(signInRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                                signInRequest.getPassword()));
        String jwt = iJwtService.generateToken(user);
        String refreshToken = iJwtService.generateRefreshToken(new HashMap<>(), user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken
            (RefreshTokenRequest refreshTokenRequest) {
        String userEmail =
                iJwtService.extractUserName(refreshTokenRequest.getToken());
        ApplicationUser applicationUser = appUserRepo.findByEmail(userEmail).orElseThrow();
        if (iJwtService.isTokenValid(refreshTokenRequest.getToken(), applicationUser)) {
            String jwt = iJwtService.generateToken(applicationUser);
            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }
}

