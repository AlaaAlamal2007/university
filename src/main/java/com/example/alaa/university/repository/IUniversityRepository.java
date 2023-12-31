package com.example.alaa.university.repository;

import com.example.alaa.university.domain.University;

import java.util.List;

public interface IUniversityRepository {
    University get(Long id);

    University add(University university);

    University update(Long id, University updatedUniversity);

    void delete(Long id);

    List<University> getAll();

    University setUniversityAddressIdNull(Long universityId);

    University getStudentUniversityId(Long studentId);

    University setUniversityAddressId(Long universityId, Long addressId);
}
