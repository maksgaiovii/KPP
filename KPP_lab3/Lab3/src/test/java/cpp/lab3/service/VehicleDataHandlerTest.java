package cpp.lab3.service;

import cpp.lab3.model.Owner;
import cpp.lab3.model.Vehicle;
import cpp.lab3.service.impl.JsonVehicleDataHandler;
import cpp.lab3.service.impl.NativeSerializedVehicleDataHandler;
import cpp.lab3.service.impl.VehicleDataHandlerImpl;
import cpp.lab3.service.impl.YamlVehicleDataHandler;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;


class VehicleDataHandlerTest {
    
    @TempDir
    Path tempDir;
    
    static Stream<VehicleDataHandler> provideImplementations() {
        return Stream.of(
                new JsonVehicleDataHandler(),
                new NativeSerializedVehicleDataHandler(),
                new VehicleDataHandlerImpl(),
                new YamlVehicleDataHandler()
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideImplementations")
    void testWriteAndReadVehicles(VehicleDataHandler handler) throws IOException, ClassNotFoundException {
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle("ABC123", "Toyota", 2015, new Owner("John", "Doe", 30)),
                new Vehicle("XYZ789", "Honda", 2001, new Owner("Jane", "Smith", 25))
        );
        
        File testFile = tempDir.resolve("test_vehicles.dat").toFile();
        handler.writeVehicles(vehicles, testFile.getAbsolutePath());
        List<Vehicle> readVehicles = handler.readVehicles(testFile.getAbsolutePath());
        
        assertEquals(vehicles, readVehicles);
    }
    
    @ParameterizedTest
    @MethodSource("provideImplementations")
    void testWriteAndReadEmptyVehicles(VehicleDataHandler handler) throws IOException, ClassNotFoundException {
        List<Vehicle> vehicles = new ArrayList<>();
        
        File testFile = tempDir.resolve("test_vehicles_empty.dat").toFile();
        handler.writeVehicles(vehicles, testFile.getAbsolutePath());
        List<Vehicle> readVehicles = handler.readVehicles(testFile.getAbsolutePath());
        
        assertEquals(vehicles, readVehicles);
    }
    
    @ParameterizedTest
    @MethodSource("provideImplementations")
    void testWriteAndReadVehiclesWithNullOwner(VehicleDataHandler handler) throws IOException, ClassNotFoundException {
        List<Vehicle> vehicles = Arrays.asList(
                new Vehicle("DEF456", "Ford", 2009, null),
                new Vehicle("GHI012", "Chevrolet", 2019, null)
        );
        
        File testFile = tempDir.resolve("test_vehicles_null_owner.dat").toFile();
        handler.writeVehicles(vehicles, testFile.getAbsolutePath());
        List<Vehicle> readVehicles = handler.readVehicles(testFile.getAbsolutePath());
        
        assertEquals(vehicles, readVehicles);
    }
}