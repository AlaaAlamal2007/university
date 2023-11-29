package com.example.alaa.university.config;

import com.example.alaa.university.domain.Address;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
@Component
public class AddressRowMapper implements RowMapper<Address> {
    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address=new Address();
        address.setId(rs.getLong(1));
        address.setCityName(rs.getString(2).strip());
        address.setStreetName(rs.getString(3).strip());
        address.setStreetNumber(rs.getInt(4));
        return address;
    }
}
