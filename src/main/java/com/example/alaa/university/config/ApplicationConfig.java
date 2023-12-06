package com.example.alaa.university.config;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

@Configuration
public class ApplicationConfig {

    @Bean
    public RowMapper<Address> addressRowMapper(){
        return new BeanPropertyRowMapper<>(Address.class);
    }
    @Bean
    public RowMapper<Student> studentRowMapper(){
        return new BeanPropertyRowMapper<>(Student.class);
    }
    @Bean
    public RowMapper<University> universityRowMapper(){
        return new BeanPropertyRowMapper<>(University.class);
    }
}
