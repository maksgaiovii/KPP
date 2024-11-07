package org.example;
import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

import static org.junit.Assert.*;

class VehicleManagerTest {
    private VehicleManager manager;
    private static final String TEST_VEHICLES_FILE = "test_vehicles.txt";
    private static final String TEST_NATIVE_FILE = "test_vehicles.txt";
    private static final String TEST_JSON_FILE = "test_vehicles.json";
    private static final String TEST_YAML_FILE = "test_vehicles.yaml";

    @BeforeEach
    void setUp() throws IOException {
        // Створюємо новий менеджер перед кожним тестом
        manager = new VehicleManager() {
            // Перевизначаємо константу для тестового файлу
            protected String getVehiclesFile() {
                return TEST_VEHICLES_FILE;
            }
        };

    }

    @AfterEach
    void tearDown() throws IOException {

    }

    private void deleteTestFiles() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_VEHICLES_FILE));
        Files.deleteIfExists(Paths.get(TEST_NATIVE_FILE));
        Files.deleteIfExists(Paths.get(TEST_JSON_FILE));
        Files.deleteIfExists(Paths.get(TEST_YAML_FILE));
    }

    @Test
    void testAddVehicle() throws IOException {
        // Given
        Vehicle vehicle = new Vehicle("AA1234BB", "Toyota", 2015);

        // When
        manager.addVehicle(vehicle);

        // Then
        List<Vehicle> vehicles = manager.getAllVehicles();
        assertEquals(1, vehicles.size());
        assertEquals(vehicle.getLicensePlate(), vehicles.get(0).getLicensePlate());

        // Перевіряємо, що дані записались у файл
        String fileContent = Files.readString(Paths.get(TEST_VEHICLES_FILE));
        assertTrue(fileContent.contains("License Plate: AA1234BB"));
        assertTrue(fileContent.contains("Brand: Toyota"));
        assertTrue(fileContent.contains("Year: 2015"));
    }

    @Test
    void testLoadVehiclesFromFile() throws IOException {
        // Given
        Vehicle vehicle1 = new Vehicle("AA1234BB", "Toyota", 2015);
        Vehicle vehicle2 = new Vehicle("BC5678DE", "Honda", 2008);
        manager.addVehicle(vehicle1);
        manager.addVehicle(vehicle2);

        // When
        VehicleManager newManager = new VehicleManager() {
            protected String getVehiclesFile() {
                return TEST_VEHICLES_FILE;
            }
        };
        newManager.loadVehiclesFromFile();

        // Then
        List<Vehicle> loadedVehicles = newManager.getAllVehicles();
        assertEquals(2, loadedVehicles.size());
        assertEquals("AA1234BB", loadedVehicles.get(0).getLicensePlate());
        assertEquals("BC5678DE", loadedVehicles.get(1).getLicensePlate());
    }

    @Test
    void testRemoveVehicle() throws IOException {
        // Given
        Vehicle vehicle = new Vehicle("AA1234BB", "Toyota", 2015);
        manager.addVehicle(vehicle);

        // When
        manager.removeVehicle("AA1234BB");

        // Then
        List<Vehicle> vehicles = manager.getAllVehicles();
        assertTrue(vehicles.isEmpty());

        // Перевіряємо, що дані видалились з файлу
        String fileContent = Files.readString(Paths.get(TEST_VEHICLES_FILE));
        assertFalse(fileContent.contains("AA1234BB"));
    }

    @Test
    void testFindByLicensePlate() {
        // Given
        Vehicle vehicle = new Vehicle("AA1234BB", "Toyota", 2015);
        manager.addVehicle(vehicle);

        // When
        Vehicle found = manager.findByLicensePlate("AA1234BB");

        // Then
        assertNotNull(found);
        assertEquals("AA1234BB", found.getLicensePlate());
        assertEquals("Toyota", found.getBrand());
        assertEquals(2015, found.getYear());
    }

    @Test
    void testNativeSerialization() throws IOException {
        // Given
        Vehicle vehicle = new Vehicle("AA1234BB", "Toyota", 2015);
        manager.addVehicle(vehicle);

        // When
        manager.saveToNativeFormat(TEST_NATIVE_FILE);
        manager = new VehicleManager(); // Створюємо новий менеджер
        manager.loadFromNativeFormat(TEST_NATIVE_FILE);

        // Then
        List<Vehicle> vehicles = manager.getAllVehicles();
        assertEquals(1, vehicles.size());
        assertEquals("AA1234BB", vehicles.get(0).getLicensePlate());
        // Перевіряємо, що рік випуску не серіалізувався
        assertEquals(0, vehicles.get(0).getYear());
    }

    @Test
    void testJsonSerialization() throws IOException {
        // Given
        Vehicle vehicle = new Vehicle("AA1234BB", "Toyota", 2015);
        manager.addVehicle(vehicle);

        // When
        manager.saveToJson(TEST_JSON_FILE);
        manager = new VehicleManager(); // Створюємо новий менеджер
        manager.loadFromJson(TEST_JSON_FILE);

        // Then
        List<Vehicle> vehicles = manager.getAllVehicles();
        Assertions.assertEquals(1, vehicles.size());
        assertEquals("AA1234BB", vehicles.get(0).getLicensePlate());
        // Перевіряємо, що рік випуску не серіалізувався
        assertEquals(0, vehicles.get(0).getYear());
    }

    @Test
    void testYamlSerialization() throws IOException {
        // Given
        Vehicle vehicle1 = new Vehicle("AA1234BB", "Toyota", 2015);
        Vehicle vehicle2 = new Vehicle("BC5678DE", "Honda", 2008);
        manager.addVehicle(vehicle1);
        manager.addVehicle(vehicle2);

        // When
        manager.saveToYaml(TEST_YAML_FILE);
        manager = new VehicleManager(); // Створюємо новий менеджер
        manager.loadFromYaml(TEST_YAML_FILE);

        // Then
        List<Vehicle> vehicles = manager.getAllVehicles();
        assertEquals(2, vehicles.size());
        // Перевіряємо, що марка відсутня для авто до 2010 року
        assertNull(vehicles.stream()
                .filter(v -> v.getLicensePlate().equals("BC5678DE"))
                .findFirst()
                .get()
                .getBrand());
    }

}
