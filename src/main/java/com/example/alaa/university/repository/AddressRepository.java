package com.example.alaa.university.repository;

import com.example.alaa.university.domain.Address;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class AddressRepository implements IAddressRepository {
    private JdbcTemplate jdbcTemplateAdd;
    private RowMapper<Address> rowMapper;



    public AddressRepository(JdbcTemplate jdbcTemplateAdd, RowMapper<Address> addressRowMapper) {
        this.jdbcTemplateAdd = jdbcTemplateAdd;
        this.rowMapper = addressRowMapper;
    }


    @Override
    public Address get(Long id) {
        Address address = jdbcTemplateAdd.queryForObject("SELECT id, city_name, street_name, street_number\n" +
                "\tFROM public.addresses where id=?", rowMapper, id);

        return address;
    }

    @Override
    public Address add(Address addressToBeAdded) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO public.addresses(\n" +
                            " city_name, street_name, street_number)\n" +
                            "\tVALUES ( ?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, addressToBeAdded.getCityName());
            preparedStatement.setString(2, addressToBeAdded.getStreetName());
            preparedStatement.setInt(3, addressToBeAdded.getStreetNumber());
            return preparedStatement;
        };

        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplateAdd.update(preparedStatementCreator, generatedKeyHolder);
        Number keyGenerated = generatedKeyHolder.getKey();
        Long key = keyGenerated.longValue();

        return get(key);
    }


    @Override
    public void delete(Long id) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.addresses\n" +
                    "\tWHERE id=?");
            preparedStatement.setLong(1, id);

            return preparedStatement;

        };
        jdbcTemplateAdd.update(preparedStatementCreator);


    }

    @Override
    public List<Address> getAll() {

        return jdbcTemplateAdd.query("SELECT id, city_name, street_name, street_number\n" +
                "\tFROM public.addresses", rowMapper);
    }

    @Override
    public Address update(Long id, Address updatedAddress) {
//        System.out.println("entervaddress upadte");
//        System.out.println("inside update add" + updatedAddress.getCityName());

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.addresses\n" +
                    "\tSET  city_name=?, street_name=?, street_number=?\n" +
                    "\tWHERE id=?");
            preparedStatement.setString(1, updatedAddress.getCityName());
            preparedStatement.setString(2, updatedAddress.getStreetName());
            preparedStatement.setInt(3, updatedAddress.getStreetNumber());
            preparedStatement.setLong(4, updatedAddress.getId());
            return preparedStatement;
        };
        jdbcTemplateAdd.update(preparedStatementCreator);
        return get(id);
    }
}