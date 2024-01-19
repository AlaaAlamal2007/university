package com.example.alaa.university.repository;

import com.example.alaa.university.domain.ApplicationUser;
import com.example.alaa.university.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationUserRepo extends JpaRepository<ApplicationUser, Long> {
    Optional<ApplicationUser> findByEmail(String email);

    ApplicationUser findByRole(Role role);
}

