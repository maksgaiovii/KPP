package Tests;
import Entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RouteTest {

    private Route route;
    private Flight flight1;
    private Flight flight2;
    private Flight flight3;

    @BeforeEach
    public void setUp() {
        // Creating individual flights
        flight1 = new Flight("FL123", "New York", "Chicago", "2024-10-10 08:00", "2024-10-10 10:00", 150, "Boeing 747");
        flight2 = new Flight("FL456", "Chicago", "Los Angeles", "2024-10-10 12:00", "2024-10-10 14:30", 200, "Airbus A320");
        flight3 = new Flight("FL789", "Los Angeles", "San Francisco", "2024-10-10 16:00", "2024-10-10 17:30", 100, "Boeing 737");

        // Initializing a route with a list of flights
        List<Flight> flights = Arrays.asList(flight1, flight2, flight3);
        route = new Route(flights);
    }

    @Test
    public void testGetTotalTime() {
        // Total time includes flight times and layover times
        // Flight1: 2 hours (120 minutes)
        // Layover 1: 2 hours (120 minutes)
        // Flight2: 2.5 hours (150 minutes)
        // Layover 2: 1.5 hours (90 minutes)
        // Flight3: 1.5 hours (90 minutes)
        long expectedTotalTime = 120 + 120 + 150 + 90 + 90;
        assertEquals(expectedTotalTime, route.getTotalTime());
    }

    @Test
    public void testGetTotalPrice() {
        // Total price is the sum of flight prices: 150 + 200 + 100 = 450
        double expectedTotalPrice = 450;
        assertEquals(expectedTotalPrice, route.getTotalPrice());
    }

    @Test
    public void testGetFlights() {
        // Verify that the list of flights in the route is correct
        List<Flight> expectedFlights = Arrays.asList(flight1, flight2, flight3);
        assertEquals(expectedFlights, route.getFlights());
    }
}

