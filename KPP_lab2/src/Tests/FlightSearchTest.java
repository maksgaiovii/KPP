package Tests;
import Entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class FlightSearchTest {

    private Flight flight1;
    private Flight flight2;
    private Flight flight3;
    private Flight flight4;
    private FlightSearch flightSearch;

    @BeforeEach
    public void setUp() {
        // Sample flights
        flight1 = new Flight("FL123", "New York", "Chicago", "2024-10-10 08:00", "2024-10-10 10:00", 150, "Boeing 747");
        flight2 = new Flight("FL456", "Chicago", "Los Angeles", "2024-10-10 12:00", "2024-10-10 14:30", 200, "Airbus A320");
        flight3 = new Flight("FL789", "Chicago", "San Francisco", "2024-10-10 12:30", "2024-10-10 15:00", 180, "Boeing 737");
        flight4 = new Flight("FL987", "Los Angeles", "San Francisco", "2024-10-10 16:00", "2024-10-10 17:30", 100, "Boeing 777");

        // Initialize FlightSearch with flights
        List<Flight> flights = Arrays.asList(flight1, flight2, flight3, flight4);
        flightSearch = new FlightSearch(flights);
    }

    @Test
    public void testFindOptimalRoutes_SimpleRoute() {
        // Testing a direct flight route from Los Angeles to San Francisco
        List<Route> routes = flightSearch.findOptimalRoutes("Los Angeles", "San Francisco");

        assertEquals(1, routes.size());
        assertEquals(1, routes.get(0).getFlights().size());
        assertEquals("FL987", routes.get(0).getFlights().get(0).getFlightNumber());
    }

    @Test
    public void testFindOptimalRoutes_MultipleLegs() {
        // Testing a route from New York to San Francisco (with layovers)
        List<Route> routes = flightSearch.findOptimalRoutes("New York", "San Francisco");

        assertEquals(2, routes.size());

        Route fastestRoute = routes.get(0); // Should be sorted by total time and price

        assertEquals(2, fastestRoute.getFlights().size());
        assertEquals("FL123", fastestRoute.getFlights().get(0).getFlightNumber());
        assertEquals("FL789", fastestRoute.getFlights().get(1).getFlightNumber());
    }

    @Test
    public void testFindOptimalRoutes_NoPossibleRoute() {
        // Testing a case with no valid route
        List<Route> routes = flightSearch.findOptimalRoutes("Chicago", "New York");

        assertTrue(routes.isEmpty());
    }

    @Test
    public void testRouteSortingByTimeAndPrice() {
        // Testing the sorting of routes by time and price
        List<Route> routes = flightSearch.findOptimalRoutes("New York", "San Francisco");

        assertEquals(2, routes.size());

        Route firstRoute = routes.get(0);
        Route secondRoute = routes.get(1);

        // Verify that the first route is faster
        assertTrue(firstRoute.getTotalTime() < secondRoute.getTotalTime());
        // Verify that the second route is slower but potentially more expensive
        assertTrue(firstRoute.getTotalPrice() <= secondRoute.getTotalPrice());
    }
}

