package com.example.alaa.university.repository;

import com.example.alaa.university.domain.University;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public class UniversityRepository implements IUniversityRepository {
    private final JdbcTemplate jdbcTemplateU;
    private final RowMapper<University> rowMapperU;

    public UniversityRepository(JdbcTemplate jdbcTemplateU,
                                RowMapper<University> universityRowMapper
    ) {
        this.jdbcTemplateU = jdbcTemplateU;
        this.rowMapperU = universityRowMapper;
    }

    @Override
    public University get(Long id) {
        University university = null;
        try {
            university = jdbcTemplateU.queryForObject("SELECT id, name, university_type, email, study_cost, start_operating_date\n" +
                    "\tFROM public.universities where id=?", rowMapperU, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return university;
    }

    @Override
    public University add(University university) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO public.universities(\n" +
                            "\t name, university_type, email, study_cost,  start_operating_date)\n" +
                            "\tVALUES (  ?, ?, ?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, university.getName());
            preparedStatement.setString(2, university.getUniversityType().toString());
            preparedStatement.setString(3, university.getEmail());
            preparedStatement.setDouble(4, university.getStudyCost());
            preparedStatement.setTimestamp(5,
                    Optional.ofNullable(university.getStartOperatingDate())
                            .map(instant -> Timestamp.from(university.getStartOperatingDate()))
                            .orElse(null));
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        jdbcTemplateU.update(preparedStatementCreator, generatedKeyHolder);
        Number keyGenerated = generatedKeyHolder.getKey();
        Long key = keyGenerated.longValue();
        return get(key);
    }

    @Override
    public University update(Long id, University updatedUniversity) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE public.universities\n" +
                            "\tSET  name=?, university_type=?, email=?, study_cost=?, address_id=?, start_operating_date=?\n" +
                            "\tWHERE id=?");
            preparedStatement.setString(1, updatedUniversity.getName());
            preparedStatement.setString(2, updatedUniversity.getUniversityType().toString());
            preparedStatement.setString(3, updatedUniversity.getEmail());
            preparedStatement.setDouble(4, updatedUniversity.getStudyCost());
            preparedStatement.setLong(5,
                    Optional.ofNullable(updatedUniversity.getAddress().getId())
                            .orElse(null));
            preparedStatement.setTimestamp(6,
                    Optional.ofNullable(updatedUniversity.getStartOperatingDate()).map(instant -> Timestamp.from(instant)).orElse(null));
            preparedStatement.setLong(7, id);
            return preparedStatement;
        };
        jdbcTemplateU.update(preparedStatementCreator);
        return get(id);
    }

    @Override
    public void delete(Long id) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.universities\n" +
                    "\tWHERE id=?");
            preparedStatement.setLong(1, id);
            return preparedStatement;
        };
        jdbcTemplateU.update(preparedStatementCreator);
    }

    @Override
    public List<University> getAll() {
        return jdbcTemplateU.query("select * from universities", rowMapperU);
    }

    @Override
    public University setUniversityAddressIdNull(Long universityId) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.universities\n" +
                    "\tSET  address_id=null\n" +
                    "\tWHERE id=?;");
            preparedStatement.setLong(1, universityId);
            return preparedStatement;
        };
        jdbcTemplateU.update(preparedStatementCreator);
        return get(universityId);
    }

    @Override
    public University getStudentUniversityId(Long studentId) {
        University university = null;
        try {
            university = jdbcTemplateU.queryForObject("select u.id,u.name,u.university_type,u.email,u.study_cost,u.start_operating_date\n" +
                    "from universities as u \n" +
                    "left join students as s on s.university_id=u.id\n" +
                    "where s.id=?", rowMapperU, studentId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return university;
    }

    @Override
    public University setUniversityAddressId(Long universityId, Long addressId) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.universities\n" +
                    "\tSET  address_id=?\n" +
                    "\tWHERE id=?"
            );
            preparedStatement.setLong(1, addressId);
            preparedStatement.setLong(2, universityId);
            return preparedStatement;
        };
        jdbcTemplateU.update(preparedStatementCreator);
        return get(universityId);
    }
}
