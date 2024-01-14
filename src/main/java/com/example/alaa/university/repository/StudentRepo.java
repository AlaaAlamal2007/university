package com.example.alaa.university.repository;

import com.example.alaa.university.domain.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepo extends JpaRepository<Student, Long> {
    @Transactional
    void deleteAllStudentByUniversityId(Long universityId);

    List<Student> findAllByUniversityId(long universityId);

    Student findByUniversityId(Long universityId);
}



