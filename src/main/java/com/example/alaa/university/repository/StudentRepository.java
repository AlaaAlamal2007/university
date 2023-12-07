package com.example.alaa.university.repository;
import com.example.alaa.university.domain.Student;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class StudentRepository implements IStudentRepository {
    private final JdbcTemplate template;
    private final RowMapper<Student> rowMapper;
    public StudentRepository(JdbcTemplate template, RowMapper<Student> studentRowMapper
    ) {
        System.out.println(template.toString());
        this.template = template;
        this.rowMapper = studentRowMapper;
    }
    @Override
    public Student get(Long id) {
        Student student = null;
        try {
            student = template.queryForObject("SELECT id, name, gender, graduated, payment_fee, email, address_id, birth_date, graduated_date, registration_date\n" +
                    "\tFROM public.students where id=?", rowMapper, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return student;
    }
    @Override
    public Student add(Student studentToAdd, long University_id) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO public.students(\n" +
                            "\t name, gender, graduated, payment_fee, email, university_id," +
                            "  birth_date, graduated_date, registration_date)\n" +
                            "\tVALUES (  ?, ?, ?, ?, ?, ?, ?, ?, ?);", new String[]{"id"});
            preparedStatement.setString(1, studentToAdd.getName());
            preparedStatement.setString(2, studentToAdd.getGender().toString());
            preparedStatement.setBoolean(3, studentToAdd.getGraduated());
            preparedStatement.setDouble(4, studentToAdd.getPaymentFee());
            preparedStatement.setString(5, studentToAdd.getEmail());
            preparedStatement.setLong(6, University_id);

            preparedStatement.setTimestamp(7,
                    Optional.ofNullable(studentToAdd.getBirthDate())
                            .map(instant -> Timestamp.from(instant))
                            .orElse(null)
            );
            preparedStatement.setTimestamp(8,
                    Optional.ofNullable(studentToAdd.getGraduatedDate())
                            .map(instant -> Timestamp.from(instant))
                            .orElse(null)
            );
            preparedStatement.setTimestamp(9,
                    Optional.ofNullable(studentToAdd.getRegistrationDate())
                            .map(instant -> Timestamp.from(instant))
                            .orElse(null)
            );
            return preparedStatement;
        };
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        template.update(preparedStatementCreator, generatedKeyHolder);
        Number keyGenerated = generatedKeyHolder.getKey();
        Long key = keyGenerated.longValue();
        return get(key);
    }
    @Override
    public Student update(Long id, Student updatedStudent) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE public.students\n" +
                            "\tSET  name=?, gender=?, graduated=?, payment_fee=?, email=?, birth_date=?, graduated_date=?, registration_date=?\n" +
                            "\tWHERE id=?");
            preparedStatement.setString(1, updatedStudent.getName());
            preparedStatement.setString(2, updatedStudent.getGender().toString());
            preparedStatement.setBoolean(3, updatedStudent.getGraduated());
            preparedStatement.setDouble(4, updatedStudent.getPaymentFee());
            preparedStatement.setString(5, updatedStudent.getEmail());
          //  preparedStatement.setLong(6, Optional.ofNullable(updatedStudent.getAddress().getId()).orElse(null));
            preparedStatement.setTimestamp(6,
                    Optional.ofNullable(updatedStudent.getBirthDate())
                            .map(instant -> Timestamp.from(instant))
                            .orElse(null)
            );
            preparedStatement.setTimestamp(7,
                    Optional.ofNullable(updatedStudent.getGraduatedDate())
                            .map(instant -> Timestamp.from(instant))
                            .orElse(null)
            );
            preparedStatement.setTimestamp(8,
                    Optional.ofNullable(updatedStudent.getRegistrationDate())
                            .map(instant -> Timestamp.from(instant))
                            .orElse(null)
            );
            preparedStatement.setLong(9, id);
            return preparedStatement;
        };
        template.update(preparedStatementCreator);
        return get(id);
    }
    @Override
    public void delete(Long id) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from students where id =?");
            preparedStatement.setLong(1, id);
            return preparedStatement;
        };
        template.update(preparedStatementCreator);
    }
    @Override
    public List<Student> getAll() {
        return template.query("SELECT id, name, gender, graduated, payment_fee, email, birth_date, graduated_date, registration_date\n" +
                "\tFROM public.students;", rowMapper);

    }

    @Override
    public List<Student> getAllStudentByUniversityId(Long universityId) {

        List<Student> students =null;

            students = template.query("select * from students where university_id=?",
                    rowMapper, universityId);

        return students;
    }

    @Override
    public Student setStudentUniversityAndAddressIdNull(Long studentId){
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.students\n" +
                            "\tSET  university_id=null, address_id=null\n" +
                            "\tWHERE id=?"
                    );
            preparedStatement.setLong(1, studentId);

            return preparedStatement;
        };
        template.update(preparedStatementCreator);
        return get(studentId);
    }

    @Override
    public Student setStudentAddressId(Long studentId, Long addressId) {
        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE public.students\n" +
                    "\tSET  address_id=?\n" +
                    "\tWHERE id=?");
            preparedStatement.setLong(1, addressId);
            preparedStatement.setLong(2,studentId);
            return preparedStatement;
        };
        template.update(preparedStatementCreator);
        return get(studentId);
    }
}
