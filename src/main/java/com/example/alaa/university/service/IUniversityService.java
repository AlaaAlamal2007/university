package com.example.alaa.university.service;

import com.example.alaa.university.domain.University;

import java.util.List;

public interface IUniversityService {
    University get(Long id);

    University add(University university);

    University update(Long id, University updatedUniversity);

    void delete(Long id);

    List<University> getAll();
}
