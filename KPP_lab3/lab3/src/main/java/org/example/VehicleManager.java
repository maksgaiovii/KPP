package org.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleManager {
    private List<Vehicle> vehicles;
    private static final String VEHICLES_FILE = "vehicles.txt";

    private final Serializer nativeSerializer;
    private final Serializer jsonSerializer;
    private final Serializer yamlSerializer;

    public VehicleManager() {
        this.vehicles = new ArrayList<>();
        this.nativeSerializer = new NativeSerializer();
        this.jsonSerializer = new JsonSerializer();
        this.yamlSerializer = new YamlSerializer();
    }

    // Метод для додавання нового транспортного засобу
    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        writeVehicleToFile(vehicle);
    }

    // Запис даних транспортного засобу у файл
    private void writeVehicleToFile(Vehicle vehicle) {
        try {
            // Використовуємо буферизований потік для номерного знаку та марки
            BufferedWriter bufferedWriter = new BufferedWriter(
                    new FileWriter(VEHICLES_FILE, true)
            );

            // Записуємо номерний знак та марку через буферизований потік
            bufferedWriter.write("License Plate: " + vehicle.getLicensePlate());
            bufferedWriter.newLine();
            bufferedWriter.write("Brand: " + vehicle.getBrand());
            bufferedWriter.newLine();
            bufferedWriter.flush(); // Очищуємо буфер

            // Використовуємо небуферизований потік для року
            FileWriter unbufferedWriter = new FileWriter(VEHICLES_FILE, true);
            unbufferedWriter.write("Year: " + vehicle.getYear() + "\n");
            unbufferedWriter.write("------------------------\n"); // Роздільник між записами
            unbufferedWriter.close();

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Читання всіх транспортних засобів з файлу
    public void loadVehiclesFromFile() {
        vehicles.clear(); // Очищуємо поточний список
        try (BufferedReader reader = new BufferedReader(new FileReader(VEHICLES_FILE))) {
            String line;
            String licensePlate = null;
            String brand = null;
            int year = 0;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("License Plate: ")) {
                    licensePlate = line.substring("License Plate: ".length());
                } else if (line.startsWith("Brand: ")) {
                    brand = line.substring("Brand: ".length());
                } else if (line.startsWith("Year: ")) {
                    year = Integer.parseInt(line.substring("Year: ".length()));
                } else if (line.startsWith("------------------------")) {
                    if (licensePlate != null && brand != null) {
                        vehicles.add(new Vehicle(licensePlate, brand, year));
                    }
                    licensePlate = null;
                    brand = null;
                    year = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для очищення файлу та перезапису всіх даних
    public void saveCurrentState() {
        try {
            // Очищаємо файл
            new FileWriter(VEHICLES_FILE).close();

            // Записуємо всі транспортні засоби заново
            for (Vehicle vehicle : vehicles) {
                writeVehicleToFile(vehicle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Методи для серіалізації залишаються незмінними
    public void saveToNativeFormat(String fileName) throws IOException {
        nativeSerializer.serialize(vehicles, fileName);
    }

    public void saveToJson(String fileName) throws IOException {
        jsonSerializer.serialize(vehicles, fileName);
    }

    public void saveToYaml(String fileName) throws IOException {
        yamlSerializer.serialize(vehicles, fileName);
    }

    // Допоміжні методи
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }

    public void removeVehicle(String licensePlate) {
        vehicles.removeIf(v -> v.getLicensePlate().equals(licensePlate));
        saveCurrentState(); // Оновлюємо файл після видалення
    }

    public void loadFromNativeFormat(String fileName) {
        try {
            vehicles = nativeSerializer.deserialize(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            vehicles = new ArrayList<>();
        }
    }

    public void loadFromJson(String fileName) {
        try {
            vehicles = jsonSerializer.deserialize(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            vehicles = new ArrayList<>();
        }
    }

    public void loadFromYaml(String fileName) {
        try {
            vehicles = yamlSerializer.deserialize(fileName);
        } catch (Exception e) {
            e.printStackTrace();
            vehicles = new ArrayList<>();
        }
    }

    public Vehicle findByLicensePlate(String licensePlate) {
        return vehicles.stream()
                .filter(v -> v.getLicensePlate().equals(licensePlate))
                .findFirst()
                .orElse(null);
    }
}