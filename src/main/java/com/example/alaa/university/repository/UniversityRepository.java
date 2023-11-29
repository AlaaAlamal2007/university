package com.example.alaa.university.repository;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.University;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.List;


//import java.util.List;
@Component
public class UniversityRepository implements IUniversityRepository {
    private final JdbcTemplate jdbcTemplateU;
    private final RowMapper<University> rowMapperU;
    private final IAddressRepository iAddressRepository;
    private final IStudentRepository iStudentRepository;

    public UniversityRepository(JdbcTemplate jdbcTemplateU,
                                RowMapper<University> universityRowMapper,
                                IAddressRepository iAddressRepository, IStudentRepository iStudentRepository) {
        this.jdbcTemplateU = jdbcTemplateU;
        this.rowMapperU = universityRowMapper;
        this.iAddressRepository = iAddressRepository;
        this.iStudentRepository = iStudentRepository;
    }

    @Override
    public University get(Long id) {
        University university = jdbcTemplateU.queryForObject("SELECT id, name, university_type, email, study_cost, address_id, start_operating_date\n" +
                "\tFROM public.universities where id=?", rowMapperU, id);
        return university;
    }

    @Override
    public University add(University university) {

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO public.universities(\n" +
                            "\t name, university_type, email, study_cost, address_id, start_operating_date)\n" +
                            "\tVALUES ( ?, ?, ?, ?, ?, ?)", new String[]{"id"});
            preparedStatement.setString(1, university.getName());
            preparedStatement.setString(2, university.getUniversityType().toString());
            preparedStatement.setString(3, university.getEmail());
            preparedStatement.setDouble(4, university.getStudyCost());
            preparedStatement.setLong(5, university.getAddress().getId());
            preparedStatement.setTimestamp(6, Timestamp.from(university.getStartOperatingDate()));


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
        University universityBeforeUpdated = this.get(id);
        updatedUniversity.getAddress().setId(universityBeforeUpdated.getAddress().getId());
        Address newAddress = iAddressRepository.update(universityBeforeUpdated.getAddress().getId(),
                updatedUniversity.getAddress());


        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("\t UPDATE public.universities SET  name=?, university_type=?, email=?, study_cost=?, " +
                            " start_operating_date=?\n where id=?");
            preparedStatement.setString(1, updatedUniversity.getName());
            preparedStatement.setString(2, updatedUniversity.getUniversityType().toString());
            preparedStatement.setString(3, updatedUniversity.getEmail());
            preparedStatement.setDouble(4, updatedUniversity.getStudyCost());
            preparedStatement.setTimestamp(5, Timestamp.from(updatedUniversity.getStartOperatingDate()));
            preparedStatement.setLong(6, id);

            return preparedStatement;
        };
        jdbcTemplateU.update(preparedStatementCreator);

        return get(id);

    }

    @Override
    public void delete(Long id) {
        System.out.println("Enter");
        University university = this.get(id);
        Long addID = university.getAddress().getId();
        university.setAddress(null);


        List<Student> studentsInUni = iStudentRepository.getStudentsinUni(id);
        for (Student st : studentsInUni) {
            iStudentRepository.delete(st.getId());
        }
        System.out.println("delete all students");
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM public.universities\n" +
                    "\tWHERE id=?");
            preparedStatement.setLong(1, id);

            return preparedStatement;

        };
        jdbcTemplateU.update(preparedStatementCreator);
        iAddressRepository.delete(addID);
        System.out.println("delete address of university");
        System.out.println("delete university");

    }

    @Override
    public List<University> getAll() {

        return jdbcTemplateU.query("select * from universities", rowMapperU);
    }


}
