package cpp.lab3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Objects;

public class Vehicle implements Serializable {
    private String licensePlate;
    
    private String brand;
    
    @JsonIgnore
    private int modelYear;
    
    private Owner owner;
    
    public Vehicle(String licensePlate, String brand, int modelYear, Owner owner) {
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.modelYear = modelYear;
        this.owner = owner;
    }

    public Vehicle() {
    }

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
    
    public int getModelYear() {
        return modelYear;
    }
    
    public void setModelYear(int modelYear) {
        this.modelYear = modelYear;
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vehicle vehicle)) {
            return false;
        }
        return Objects.equals(licensePlate, vehicle.licensePlate) && Objects.equals(brand,
                                                                                    vehicle.brand) && Objects.equals(
                owner,
                vehicle.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate, brand, owner);
    }
}
