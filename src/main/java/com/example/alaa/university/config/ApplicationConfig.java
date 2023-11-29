package com.example.alaa.university.config;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;

@Configuration
public class ApplicationConfig {
    @Bean("defaultRowMapper")

    public RowMapper<Student> studentRowMapper(){

        return new BeanPropertyRowMapper<>();
    }
////@Bean("universityRowMapper")
////    public RowMapper<University> universityRowMapper(){
////        RowMapper<University> rowMapperUni=(ResultSet rs,int RowNum)->{
////            University university=new University();
////            university.setId(rs.getLong("id"));
////
////            return  university;
////
////    };
//
//       return rowMapperUni;
//}


}
