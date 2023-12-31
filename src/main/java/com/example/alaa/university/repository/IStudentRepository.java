package com.example.alaa.university.repository;

import com.example.alaa.university.domain.Student;

import java.util.List;

public interface IStudentRepository {
    Student get(Long id);

    Student add(Student student, long University_id);

    Student update(Long id, Student updatedStudent);

    void delete(Long id);

    List<Student> getAll();

    List<Student> getAllStudentByUniversityId(Long id);

    Student setStudentUniversityAndAddressIdNull(Long studentId);

    Student setStudentAddressId(Long studentId, Long addressId);
}
