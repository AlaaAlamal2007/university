package com.example.alaa.university;

import com.example.alaa.university.domain.ApplicationUser;
import com.example.alaa.university.domain.Role;
import com.example.alaa.university.repository.ApplicationUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UniversityApplication implements CommandLineRunner {
    private final ApplicationUserRepo appUserRepo;

    public UniversityApplication(ApplicationUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(UniversityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        ApplicationUser adminAccount = appUserRepo.findByRole(Role.ADMIN);
        if (adminAccount == null) {
            ApplicationUser user = new ApplicationUser();
            user.setEmail("admin@gmail.com");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setRole(Role.ADMIN);
            user.setPassword(new BCryptPasswordEncoder().encode("admin"));
            appUserRepo.save(user);
        }
    }
}





