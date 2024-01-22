package com.example.alaa.university.auditing;

import com.example.alaa.university.domain.ApplicationUser;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class ApplicationAuditAware implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken
        ) {
            return Optional.empty();
        }
        ApplicationUser userPrincipal = (ApplicationUser) authentication
                .getPrincipal();
        return Optional.ofNullable(userPrincipal.getEmail());
    }
}

