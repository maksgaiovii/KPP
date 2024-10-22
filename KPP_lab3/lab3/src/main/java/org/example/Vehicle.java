package org.example;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Vehicle {
    private String licensePlate;

    private String brand;

    @JsonIgnore
    private transient int year; // Не серіалізується

    public Vehicle(String licensePlate, String brand, int year) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.year = year;
    }

    // Геттери та сеттери
    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}

