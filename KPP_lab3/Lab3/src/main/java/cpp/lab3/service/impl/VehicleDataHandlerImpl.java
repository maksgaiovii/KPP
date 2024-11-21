package cpp.lab3.service.impl;

import cpp.lab3.model.Owner;
import cpp.lab3.model.Vehicle;
import cpp.lab3.service.VehicleDataHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDataHandlerImpl implements VehicleDataHandler {
    
    private final boolean isBuffered;
    
    public VehicleDataHandlerImpl() {
        this.isBuffered = false;
    }
    
    public VehicleDataHandlerImpl(boolean isBuffered) {
        this.isBuffered = isBuffered;
    }
    
    @Override
    public void writeVehicles(List<Vehicle> vehicles, String fileName) throws IOException {
        try (Writer writer = isBuffered
                                     ? new BufferedWriter(new FileWriter(fileName))
                                     : new FileWriter(fileName)) {
            for (Vehicle vehicle : vehicles) {
                String vehicleData = vehicle.getLicensePlate() + "," +
                                             vehicle.getBrand() + "," +
                                             vehicle.getModelYear() + ",";
                if (vehicle.getOwner() != null) {
                    vehicleData += vehicle.getOwner().getFirstName() + "," +
                                           vehicle.getOwner().getLastName() + "," +
                                           vehicle.getOwner().getAge();
                }
                vehicleData += "\n";
                
                writer.write(vehicleData);
            }
        }
    }
    
    @Override
    public List<Vehicle> readVehicles(String fileName) throws IOException {
        try (Reader fileReader = isBuffered
                                         ? new BufferedReader(new FileReader(fileName))
                                         : new FileReader(fileName)) {
            List<Vehicle> vehicles = new ArrayList<>();
            StringBuilder lineBuilder = new StringBuilder();
            int ch;
            while ((ch = fileReader.read()) != -1) {
                if (ch == '\n') {
                    vehicles.add(parseVehicle(lineBuilder));
                    lineBuilder.setLength(0);
                } else {
                    lineBuilder.append((char) ch);
                }
            }
            // Handle the last line if it doesn't end with a newline
            if (!lineBuilder.isEmpty()) {
                vehicles.add(parseVehicle(lineBuilder));
            }
            return vehicles;
        }
    }
    
    private Vehicle parseVehicle(StringBuilder lineBuilder) {
        String line = lineBuilder.toString();
        String[] data = line.split(",", -1);
        Owner owner = null;
        if (!data[3].isEmpty() && !data[4].isEmpty() && !data[5].isEmpty()) {
            owner = new Owner(data[3], data[4], Integer.parseInt(data[5]));
        }
        return new Vehicle(data[0], data[1], Integer.parseInt(data[2]), owner);
    }
}
