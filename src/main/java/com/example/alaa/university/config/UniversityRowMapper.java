package com.example.alaa.university.config;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.University;
import com.example.alaa.university.domain.UniversityType;
import com.example.alaa.university.repository.IAddressRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
@Primary
public class UniversityRowMapper implements RowMapper<University> {


    private IAddressRepository iAddressRepository;

    public UniversityRowMapper(IAddressRepository iAddressRepository) {
        this.iAddressRepository = iAddressRepository;
    }

    @Override
    public University mapRow(ResultSet rs, int rowNum) throws SQLException {
        University university=new University();
        try{

      university.setId(rs.getLong("id"));
      university.setName(rs.getString("name"));
      String universityType=rs.getString("university_type");
      university.setUniversityType(
              universityType.equals("GOVEREMENTAL")?UniversityType.GOVERMENTAL:UniversityType.PRIVATE
      );
      university.setEmail(rs.getString("email"));
      university.setStudyCost(rs.getDouble("study_cost"));
            Address address=iAddressRepository.get(rs.getLong("address_id"));
            university.setAddress(address);
            university.setStartOperatingDate(rs.getTimestamp("start_operating_date").toInstant());

        }catch (NullPointerException e){
           // System.out.println("Enter null value");
        }

        return university;
    }
}
