package com.example.alaa.university.service;
import com.example.alaa.university.repository.ApplicationUserRepo;
import org.springframework.stereotype.Service;
@Service
public class ApplicationUserService  {
    private final ApplicationUserRepo appUserRepo;

    public ApplicationUserService(ApplicationUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }
}

