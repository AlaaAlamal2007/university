package com.example.alaa.university.config;

import com.example.alaa.university.domain.Role;
import com.example.alaa.university.service.ApplicationUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.alaa.university.domain.Permission.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final ApplicationUserService appService;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
                                 ApplicationUserService appService) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.appService = appService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers("/api/v1/default").hasAnyAuthority(Role.DEFAULT.name())
                        .requestMatchers("/api/universities").hasAnyAuthority(Role.ADMIN.name())
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyAuthority(DEFAULT_READ.name())
                        .requestMatchers(HttpMethod.GET, "/api/universities/**").hasAnyAuthority(DEFAULT_READ.name())
                        .requestMatchers(HttpMethod.PUT, "/api/students/**").hasAnyAuthority(EDITOR_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/universities/**").hasAnyAuthority(EDITOR_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/teachers/**").hasAnyAuthority(EDITOR_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/subjects/**").hasAnyAuthority(EDITOR_CREATE.name())
                        .requestMatchers(HttpMethod.DELETE,"/api/teachers/**").hasAnyAuthority(EDITOR_DELETE.name())
                        .requestMatchers("/api/**").hasAnyAuthority(Role.ADMIN.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();

    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

