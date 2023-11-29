package com.example.alaa.university.config;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Gender;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.repository.IAddressRepository;
import com.fasterxml.jackson.databind.deser.impl.BeanPropertyMap;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.beans.BeanProperty;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Optional;

@Component

public class StudentRowMapper implements RowMapper<Student> {

    private IAddressRepository iAddressRepository;

    public StudentRowMapper(IAddressRepository iAddressRepository) {
        this.iAddressRepository = iAddressRepository;
    }

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {

        Student student = new Student();
        String name = rs.getString("name");
        student.setName(name.strip());
        student.setId(rs.getLong("id"));
        String gender = rs.getString("gender");

        student.setGender(gender.strip().equals("FEMALE") ? Gender.FEMALE : Gender.MALE);

//        Instant birthdate =
//                Optional.ofNullable(rs.getTimestamp(5).toInstant())
//                        .orElse(null);
        try {
            student.setBirthDate(rs.getTimestamp("birth_date").toInstant());
            student.setRegistrationDate(rs.getTimestamp("registration_date").toInstant());
            student.setGraduatedDate(rs.getTimestamp("graduated_date").toInstant());

        } catch (NullPointerException e) {

            //System.out.println("null ");
        }


        student.setGraduated(rs.getBoolean("graduated"));

//        Instant registrationDate = Optional.ofNullable(rs.getTimestamp(5).toInstant())
//                .orElse(null);


//        Instant graduatedDate = Optional.ofNullable(rs.getTimestamp(7).toInstant())
//                .orElse(null);


        student.setAddress(iAddressRepository.get(rs.getLong("address_id")));
        student.setPaymentFee(rs.getDouble("payment_fee"));
        student.setEmail(rs.getString("email"));

        return student;
    }
}
