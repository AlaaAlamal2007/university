package com.example.alaa.university.domain;

import java.time.Instant;

public class Student {
    private Integer id;
    private String name;
    private Address address;
    private Gender gender;
    private Boolean graduated;
    private Instant birthDate;
    private Instant registrationDate;
    private Instant graduatedDate;
    private Double paymentFee;
    private  String email;

    public Student(Integer id, String name, Address address, Gender gender, Boolean graduated, Instant birthDate,
                   Instant registrationDate, Instant graduatedDate, Double paymentFee, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.graduated = graduated;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.graduatedDate = graduatedDate;
        this.paymentFee = paymentFee;
        this.email = email;
    }
    public Student(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Boolean getGraduated() {
        return graduated;
    }

    public void setGraduated(Boolean graduated) {
        this.graduated = graduated;
    }

    public Instant getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Instant birthDate) {
        this.birthDate = birthDate;
    }

    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Instant getGraduatedDate() {
        return graduatedDate;
    }

    public void setGraduatedDate(Instant graduatedDate) {
        this.graduatedDate = graduatedDate;
    }

    public Double getPaymentFee() {
        return paymentFee;
    }

    public void setPaymentFee(Double paymentFee) {
        this.paymentFee = paymentFee;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
