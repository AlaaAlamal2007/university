package com.example.alaa.university.jms;

import com.example.alaa.university.domain.Address;
import com.example.alaa.university.domain.Student;
import com.example.alaa.university.domain.UniversityType;

import java.time.Instant;
import java.util.List;

public class JmsUniversityUpdate {
    private Long id;
    private String name;
    private Address address;
    private UniversityType universityType;
    private String email;
    private Double studyCost;
    private Instant startOperatingDate;
    private List<Student> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UniversityType getUniversityType() {
        return universityType;
    }

    public void setUniversityType(UniversityType universityType) {
        this.universityType = universityType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getStudyCost() {
        return studyCost;
    }

    public void setStudyCost(Double studyCost) {
        this.studyCost = studyCost;
    }

    public Instant getStartOperatingDate() {
        return startOperatingDate;
    }

    public void setStartOperatingDate(Instant startOperatingDate) {
        this.startOperatingDate = startOperatingDate;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}

