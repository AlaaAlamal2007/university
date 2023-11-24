package com.example.alaa.university.domain;

public class Address {
    private Long id;
    private String cityName;
    private String streetName;
    private Integer streetNumber;


    public Address(Long id, String cityName, String streetName, Integer streetNumber) {
        this.id = id;
        this.cityName = cityName;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
    }

    public Long getId() {
        return id;
    }

    public Address() {

    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public Integer getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(Integer streetNumber) {
        this.streetNumber = streetNumber;
    }
}
