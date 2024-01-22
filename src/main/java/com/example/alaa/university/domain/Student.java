package com.example.alaa.university.domain;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "students")
public class Student extends AbstractAuditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "graduated")
    private Boolean graduated;
    @Column(name = "birth_date")
    private Instant birthDate;
    @Column(name = "registration_date")
    private Instant registrationDate;
    @Column(name = "graduated_date")
    private Instant graduatedDate;
    @Column(name = "payment_fee")
    private Double paymentFee;
    @Column(name = "email")
    private String email;

    @JoinColumn(name = "university_id")
    private Long universityId;

    public Student() {
    }

    public Long getUniversityId() {
        return universityId;
    }

    public void setUniversityId(Long universityId) {
        this.universityId = universityId;
    }

    public Student(String name, Address address, Gender gender,
                   Boolean graduated, Instant birthDate, Instant registrationDate, Instant graduatedDate, Double paymentFee,
                   String email, Long university_id) {
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.graduated = graduated;
        this.birthDate = birthDate;
        this.registrationDate = registrationDate;
        this.graduatedDate = graduatedDate;
        this.paymentFee = paymentFee;
        this.email = email;
        this.universityId = university_id;
    }

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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + address +
                ", gender=" + gender +
                ", graduated=" + graduated +
                ", birthDate=" + birthDate +
                ", registrationDate=" + registrationDate +
                ", graduatedDate=" + graduatedDate +
                ", paymentFee=" + paymentFee +
                ", email='" + email + '\'' +
                '}';
    }
}

