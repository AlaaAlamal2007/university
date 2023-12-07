package com.example.alaa.university.service;

import com.example.alaa.university.domain.Student;

import java.util.List;

public interface IStudentService {
    Student get(Long id);

    Student add(Student student, long universityId);

    Student update(Long id, Student updatedStudent);

    void delete(Long id);

    List<Student> getAll();

    List<Student> getAllStudentByUniversityId(Long id);

    Student setStudentUniversityAndAddressIdNull(Long studentId);

    Student setStudentAddressId(Long studentId, Long addressId);
}
