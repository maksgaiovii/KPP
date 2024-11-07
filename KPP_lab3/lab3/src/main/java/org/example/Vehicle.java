package org.example;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

public class Vehicle {
    @Expose
    private String licensePlate;

    @Expose
    private String brand;

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

    @Override
    public String toString() {
        return "Vehicle{" +
                "licensePlate='" + licensePlate + '\'' +
                ", brand='" + brand + '\'' +
                ", year=" + year +
                '}';
    }

}

