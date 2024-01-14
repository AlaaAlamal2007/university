package com.example.alaa.university.repository;

import com.example.alaa.university.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TeacherRepo extends JpaRepository<Teacher, Long> {
    List<Teacher> findTeachersBySubjectsId(Long subjectId);

    @Modifying
    @Transactional
    @Query("Update Teacher t set t.name=:n where t.id=:i")
    Integer updateTeacherName(@Param("i") Long id,@Param("n") String name);
}


