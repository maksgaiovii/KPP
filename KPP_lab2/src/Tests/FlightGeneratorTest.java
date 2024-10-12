package Tests;
import Entities.*;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class FlightGeneratorTest {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");


    @Test
    public void testGenerateRandomFlight_ValidFlight() {
        // Generate a random flight
        Flight flight = FlightGenerator.generateRandomFlight();

        // Ensure the flight number is correctly generated
        assertTrue(flight.getFlightNumber().startsWith("Fl "));

        // Ensure the departure and arrival cities are different
        assertNotEquals(flight.getDepartureCity(), flight.getArrivalCity());

        // Ensure the departure time is before the arrival time
        assertTrue(flight.getDepartureTime().isBefore(flight.getArrivalTime()));

        // Ensure price is between 100 and 1300
        assertTrue(flight.getPrice() >= 100 && flight.getPrice() <= 1300);

        // Ensure the flight duration is at least 1 hour
        assertTrue(flight.getDuration().toHours() >= 1);
    }

    @Test
    public void testGenerateRandomFlights_UniqueFlightNumbers() {
        // Generate multiple flights
        Flight[] flights = FlightGenerator.generateRandomFlights(10);

        // Ensure that the generated flights have unique flight numbers
        Set<String> flightNumbers = new HashSet<>();
        for (Flight flight : flights) {
            flightNumbers.add(flight.getFlightNumber());
        }

        assertEquals(10, flightNumbers.size());
    }

    @Test
    public void testGenerateRandomFlights_ValidAmount() {
        // Generate a specific number of flights
        int amount = 5;
        Flight[] flights = FlightGenerator.generateRandomFlights(amount);

        // Ensure that the correct number of flights is generated
        assertEquals(amount, flights.length);
    }

    @Test
    public void testGeneratedFlights_HaveDifferentCities() {
        // Generate multiple flights
        Flight[] flights = FlightGenerator.generateRandomFlights(10);

        // Ensure that no flight has the same departure and arrival city
        for (Flight flight : flights) {
            assertNotEquals(flight.getDepartureCity(), flight.getArrivalCity());
        }
    }

    @Test
    public void testGeneratedFlights_ValidDateFormat() {
        // Generate a random flight
        Flight flight = FlightGenerator.generateRandomFlight();

        // Ensure that the departure and arrival times are correctly formatted
        assertDoesNotThrow(() -> {
            LocalDateTime.parse(flight.getDepartureTime().format(formatter), formatter);
            LocalDateTime.parse(flight.getArrivalTime().format(formatter), formatter);
        });
    }
}

