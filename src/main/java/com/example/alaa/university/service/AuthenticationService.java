package com.example.alaa.university.service;

import com.example.alaa.university.domain.ApplicationUser;
import com.example.alaa.university.domain.RefreshToken;
import com.example.alaa.university.domain.Role;
import com.example.alaa.university.dto.JwtAuthenticationResponse;
import com.example.alaa.university.dto.RefreshTokenRequest;
import com.example.alaa.university.dto.SignInRequest;
import com.example.alaa.university.dto.SignUpRequest;
import com.example.alaa.university.exceptions.ResourceApplicationUserIsNotFoundException;
import com.example.alaa.university.repository.ApplicationUserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements IAuthenticationService {
    private final ApplicationUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IJwtService iJwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationService(ApplicationUserRepo appUserRepo, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, IJwtService iJwtService, RefreshTokenService refreshTokenService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.iJwtService = iJwtService;
        this.refreshTokenService = refreshTokenService;
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
                .orElseThrow(() -> new ResourceApplicationUserIsNotFoundException("user not found or password is wrong"));
        authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                                signInRequest.getPassword()));
        String jwt = iJwtService.generateToken(user);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(signInRequest.getEmail());
        jwtAuthenticationResponse.setRefreshToken(refreshToken.getToken());
        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken
            (RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenService.findByToken(refreshTokenRequest.getToken())
                .orElseThrow(() -> new RuntimeException("refresh token is not in database"));
        RefreshToken newrefreshToken = refreshTokenService.verifyExpiration(refreshToken);
        ApplicationUser applicationUser = appUserRepo.findById(refreshToken.getApplicationUser().getId()).get();
        String accessToken = iJwtService.generateToken(applicationUser);
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(accessToken);
        jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
        return jwtAuthenticationResponse;
    }


}





