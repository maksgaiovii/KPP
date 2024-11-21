package cpp.lab3.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cpp.lab3.model.Vehicle;
import cpp.lab3.service.VehicleDataHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonVehicleDataHandler implements VehicleDataHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void writeVehicles(List<Vehicle> vehicles, String fileName) throws IOException {
        objectMapper.writeValue(new File(fileName), vehicles);
    }
    
    @Override
    public List<Vehicle> readVehicles(String fileName) throws IOException {
        return objectMapper.readValue(new File(fileName), new TypeReference<>() {
        });
    }
}
