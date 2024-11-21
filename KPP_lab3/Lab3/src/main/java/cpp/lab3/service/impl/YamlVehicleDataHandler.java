package cpp.lab3.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import cpp.lab3.model.Vehicle;
import cpp.lab3.service.VehicleDataHandler;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class YamlVehicleDataHandler implements VehicleDataHandler {
    ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    
    @Override
    public void writeVehicles(List<Vehicle> vehicles, String fileName) throws IOException {
        var vehiclesBefore2010ModelYearWithNullBrand = vehicles.stream()
                                                               .map(v -> {
                                               if (v.getModelYear() < 2010) {
                                                   v.setBrand(null);
                                               }
                                               return v;
                                           })
                                                               .toList();
        objectMapper.writeValue(new File(fileName), vehiclesBefore2010ModelYearWithNullBrand);
    }
    
    @Override
    public List<Vehicle> readVehicles(String fileName) throws IOException {
        return objectMapper.readValue(new File(fileName), new TypeReference<>() {
        });
    }
}
