package cpp.lab3.service;

import cpp.lab3.model.Vehicle;

import java.io.IOException;
import java.util.List;

public interface VehicleDataHandler {
    void writeVehicles(List<Vehicle> vehicles, String fileName) throws IOException;
    
    List<Vehicle> readVehicles(String fileName) throws IOException, ClassNotFoundException;
}
