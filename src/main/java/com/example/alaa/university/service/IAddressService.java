package com.example.alaa.university.service;

import com.example.alaa.university.domain.Address;

import java.util.List;

public interface IAddressService {
    Address get(Long id);

    Address add(Address address);

    void delete(Long id);

    List<Address> getAll();

    Address update(Long id, Address updatedAddress);

    Address getStudentAddressId(Long studentId);

    Address getUniversityAddressId(Long universityId);
}
