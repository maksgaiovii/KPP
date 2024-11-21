package cpp.lab3.service.impl;

import cpp.lab3.model.Vehicle;
import cpp.lab3.service.VehicleDataHandler;

import java.io.*;
import java.util.List;

public class NativeSerializedVehicleDataHandler implements VehicleDataHandler {
    @Override
    public void writeVehicles(List<Vehicle> vehicles, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(vehicles);
        }
    }
    
    @Override
    public List<Vehicle> readVehicles(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Vehicle>) in.readObject();
        }
    }
}
