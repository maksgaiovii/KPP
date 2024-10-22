package org.example;
import org.yaml.snakeyaml.Yaml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class YamlSerializer implements Serializer {
    private final Yaml yaml = new Yaml();

    @Override
    public void serialize(List<Vehicle> vehicles, String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            List<Vehicle> filteredVehicles = vehicles.stream()
                    .map(v -> {
                        if (v.getYear() < 2010) {
                            Vehicle newVehicle = new Vehicle(
                                    v.getLicensePlate(),
                                    null, // пропускаємо марку для старих авто
                                    v.getYear()
                            );
                            return newVehicle;
                        }
                        return v;
                    })
                    .collect(Collectors.toList());
            yaml.dump(filteredVehicles, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Vehicle> deserialize(String fileName) {
        try (FileReader reader = new FileReader(fileName)) {
            return yaml.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}

