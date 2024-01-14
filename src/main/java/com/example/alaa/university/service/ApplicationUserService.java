package com.example.alaa.university.service;

import com.example.alaa.university.repository.ApplicationUserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepo appUserRepo;

    public ApplicationUserService(ApplicationUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return appUserRepo.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
            }
        };
    }
}

