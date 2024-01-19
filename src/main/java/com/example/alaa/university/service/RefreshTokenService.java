package com.example.alaa.university.service;

import com.example.alaa.university.domain.ApplicationUser;
import com.example.alaa.university.domain.RefreshToken;
import com.example.alaa.university.repository.ApplicationUserRepo;
import com.example.alaa.university.repository.RefreshTokenRepo;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    private final RefreshTokenRepo refreshTokenRepo;
    private final ApplicationUserRepo applicationUserRepo;
    private final ApplicationUserService appUserService;
    private Long refreshTokenExpiration=600000L;

    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo, ApplicationUserRepo applicationUserRepo, ApplicationUserService appUserService) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.applicationUserRepo = applicationUserRepo;
        this.appUserService = appUserService;
    }

    public RefreshToken createRefreshToken(String username) {
        ApplicationUser appUser = applicationUserRepo.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
        RefreshToken refreshToken=new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshToken.setApplicationUser(appUser);
        return refreshTokenRepo.save(refreshToken);
    }

 public Optional<RefreshToken> findByToken(String token){
        return refreshTokenRepo.findByToken(token);
    }
    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.deleteById(token.getId());
            throw new RuntimeException(token.getToken() + " Refresh token was expired. Please make a new signin request");
        }
        return token;
    }
}

