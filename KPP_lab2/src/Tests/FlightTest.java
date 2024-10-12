package Tests;
import  Entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    private Flight flight;

    @BeforeEach
    public void setUp() {
        flight = new Flight(
                "FL123",
                "New York",
                "Los Angeles",
                "2024-10-10 08:00",
                "2024-10-10 12:30",
                350,
                "Boeing 747"
        );
    }

    @Test
    public void testConstructorInitialization() {
        assertEquals("FL123", flight.getFlightNumber());
        assertEquals("New York", flight.getDepartureCity());
        assertEquals("Los Angeles", flight.getArrivalCity());
        assertEquals(350, flight.getPrice());
        assertEquals("Boeing 747", flight.getPlaneType());

        // Check departure and arrival times
        assertEquals(LocalDateTime.of(2024, 10, 10, 8, 0), flight.getDepartureTime());
        assertEquals(LocalDateTime.of(2024, 10, 10, 12, 30), flight.getArrivalTime());
    }

    @Test
    public void testDurationCalculation() {
        Duration expectedDuration = Duration.ofHours(4).plusMinutes(30);
        assertEquals(expectedDuration, flight.getDuration());
    }

    @Test
    public void testFormattedDuration() {
        String formattedDuration = flight.getFormattedDuration();
        assertEquals("4h 30m", formattedDuration);
    }

    @Test
    public void testSetters() {
        flight.setFlightNumber("FL456");
        flight.setDepartureCity("Chicago");
        flight.setArrivalCity("Miami");
        flight.setPrice(400);
        flight.setPlaneType("Airbus A320");
        flight.setDepartureTime("2024-11-10 09:00");
        flight.setArrivalTime("2024-11-10 13:00");

        assertEquals("FL456", flight.getFlightNumber());
        assertEquals("Chicago", flight.getDepartureCity());
        assertEquals("Miami", flight.getArrivalCity());
        assertEquals(400, flight.getPrice());
        assertEquals("Airbus A320", flight.getPlaneType());
        assertEquals(LocalDateTime.of(2024, 11, 10, 9, 0), flight.getDepartureTime());
        assertEquals(LocalDateTime.of(2024, 11, 10, 13, 0), flight.getArrivalTime());

        // Verify updated duration
        assertEquals("4h 0m", flight.getFormattedDuration());
    }

    @Test
    public void testInvalidDateTimeFormat() {
        assertThrows(Exception.class, () -> {
            flight.setDepartureTime("Invalid-Date-Format");
        });
    }
}

