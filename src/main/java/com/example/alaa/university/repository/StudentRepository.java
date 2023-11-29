package com.example.alaa.university.repository;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class StudentRepository implements IStudentRepository {
    private final JdbcTemplate template;
    private final RowMapper<Student> rowMapper;
    private final IAddressRepository iAddressRepository;

    public StudentRepository(JdbcTemplate template, RowMapper<Student> studentRowMapper
            , IAddressRepository iAddressRepository) {
        System.out.println(template.toString());
        this.template = template;
        this.rowMapper = studentRowMapper;
        this.iAddressRepository = iAddressRepository;

    }

    @Override
    public Student get(Long id) {
        Student student = template.queryForObject("SELECT id, name, gender, graduated, payment_fee, email, university_id, address_id, birth_date, graduated_date, registration_date\n" +
                "\tFROM public.students where id=?", rowMapper, id);
        return student;
    }

    @Override
    public Student add(Student studentToBeAddedToDataBase, long University_id) {

        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {

            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO public.students(\n" +
                            "\t name, gender, graduated, payment_fee, email, university_id," +
                            " address_id, birth_date, graduated_date, registration_date)\n" +
                            "\tVALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", new String[]{"id"});
            preparedStatement.setString(1, studentToBeAddedToDataBase.getName());
            preparedStatement.setString(2, studentToBeAddedToDataBase.getGender().toString());
            preparedStatement.setBoolean(3, studentToBeAddedToDataBase.getGraduated());
            preparedStatement.setDouble(4, studentToBeAddedToDataBase.getPaymentFee());
            preparedStatement.setString(5, studentToBeAddedToDataBase.getEmail());
            preparedStatement.setLong(6, University_id);
            preparedStatement.setLong(7, studentToBeAddedToDataBase.getAddress().getId());

            preparedStatement.setTimestamp(8,
                    new java.sql.Timestamp(studentToBeAddedToDataBase.getBirthDate().toEpochMilli()));

            preparedStatement.setTimestamp(9,
                    new java.sql.Timestamp(studentToBeAddedToDataBase.getGraduatedDate().toEpochMilli()));

            preparedStatement.setTimestamp(10,
                    new java.sql.Timestamp(studentToBeAddedToDataBase.getRegistrationDate().toEpochMilli()));


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
        Student studentBeforeUpdated = this.get(id);
        updatedStudent.getAddress().setId(studentBeforeUpdated.getAddress().getId());
        Address newAddress = iAddressRepository.update(studentBeforeUpdated.getAddress().getId(),
                updatedStudent.getAddress());


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

            preparedStatement.setTimestamp(6,
                    new java.sql.Timestamp(updatedStudent.getBirthDate().toEpochMilli()));

            preparedStatement.setTimestamp(7,
                    new java.sql.Timestamp(updatedStudent.getGraduatedDate().toEpochMilli()));

            preparedStatement.setTimestamp(8,
                    new java.sql.Timestamp(updatedStudent.getRegistrationDate().toEpochMilli()));
            preparedStatement.setLong(9, id);


            return preparedStatement;
        };
        template.update(preparedStatementCreator);
        return get(id);
    }

    @Override
    public void delete(Long id) {
        Long add=get(id).getAddress().getId();
        PreparedStatementCreator preparedStatementCreator1=(Connection connection1)->{
           PreparedStatement preparedStatement1=connection1.prepareStatement("UPDATE public.students\n" +
                   "\tSET address_id=null, university_id=null\n" +
                   "\tWHERE id=?" );

           preparedStatement1.setLong(1,id);

           return preparedStatement1;
        };
            template.update(preparedStatementCreator1);


        PreparedStatementCreator preparedStatementCreator = (Connection connection) -> {

            PreparedStatement preparedStatement = connection.prepareStatement("delete from students where id =?");
            preparedStatement.setLong(1, id);


            return preparedStatement;

        };
        template.update(preparedStatementCreator);
        iAddressRepository.delete(add);



    }

    @Override
    public List<Student> getAll() {
        return template.query("SELECT id, name, gender, graduated, payment_fee, email, university_id, address_id, birth_date, graduated_date, registration_date\n" +
                "\tFROM public.students;", rowMapper);

    }
    @Override
public List<Student> getStudentsinUni(Long university_id){

        return template.query("select * from students where university_id=?",rowMapper,university_id);
}

}
