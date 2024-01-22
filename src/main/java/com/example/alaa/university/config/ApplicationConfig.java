package com.example.alaa.university.config;

import com.example.alaa.university.auditing.ApplicationAuditAware;
import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import com.example.alaa.university.repository.ApplicationUserRepo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaAuditing
public class ApplicationConfig {
    private final ApplicationUserRepo applicationUserRepo;

    public ApplicationConfig(ApplicationUserRepo applicationUserRepo) {
        this.applicationUserRepo = applicationUserRepo;
    }

    @Bean
    public RowMapper<Address> addressRowMapper() {
        return new BeanPropertyRowMapper<>(Address.class);
    }

    @Bean
    public RowMapper<Student> studentRowMapper() {
        return new BeanPropertyRowMapper<>(Student.class);
    }

    @Bean
    public RowMapper<University> universityRowMapper() {
        return new BeanPropertyRowMapper<>(University.class);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> applicationUserRepo.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
    @Bean
    public AuditorAware<String> auditorAware() {
        return new ApplicationAuditAware();
    }
}






