package com.example.alaa.university.repository;

import com.example.alaa.university.domain.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepo extends JpaRepository<Subject, Long> {
    List<Subject> findSubjectsByTeachersId(Long teacherId);
}

