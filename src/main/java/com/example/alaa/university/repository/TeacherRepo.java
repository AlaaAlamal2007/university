package com.example.alaa.university.repository;

import com.example.alaa.university.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    List<Teacher> findTeachersBySubjectsId(Long subjectId);
}

