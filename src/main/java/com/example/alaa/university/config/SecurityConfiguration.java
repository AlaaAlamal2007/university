package com.example.alaa.university.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.alaa.university.domain.Permission.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter,
                                 AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/students/**").hasAnyAuthority(DEFAULT_READ.name(), ADMIN_READ.name())
                        .requestMatchers(HttpMethod.GET, "/api/universities/**").hasAnyAuthority(DEFAULT_READ.name(), ADMIN_READ.name())
                        .requestMatchers(HttpMethod.GET, "/api/teachers/**").hasAnyAuthority(DEFAULT_READ.name(), ADMIN_READ.name())
                        .requestMatchers(HttpMethod.GET, "/api/subjects/**").hasAnyAuthority(DEFAULT_READ.name(), ADMIN_READ.name())
                        .requestMatchers(HttpMethod.PUT, "/api/students/**").hasAnyAuthority(EDITOR_UPDATE.name(), ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/universities/**").hasAnyAuthority(EDITOR_UPDATE.name(), ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/teachers/**").hasAnyAuthority(EDITOR_UPDATE.name(), ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.PUT, "/api/subjects/**").hasAnyAuthority(EDITOR_UPDATE.name(), ADMIN_UPDATE.name())
                        .requestMatchers(HttpMethod.POST, "/api/students/**").hasAnyAuthority(EDITOR_CREATE.name(), ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.POST, "/api/universities/**").hasAnyAuthority(EDITOR_CREATE.name(), ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.POST, "/api/teachers/**").hasAnyAuthority(EDITOR_CREATE.name(), ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.POST, "/api/subjects/**").hasAnyAuthority(EDITOR_CREATE.name(), ADMIN_CREATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/teachers/**").hasAnyAuthority(EDITOR_DELETE.name(), ADMIN_DELETE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/subjects/**").hasAnyAuthority(EDITOR_DELETE.name(), ADMIN_DELETE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/students/**").hasAnyAuthority(EDITOR_DELETE.name(), ADMIN_DELETE.name())
                        .requestMatchers(HttpMethod.DELETE, "/api/universities/**").hasAnyAuthority(EDITOR_DELETE.name(), ADMIN_DELETE.name())
                        .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}


