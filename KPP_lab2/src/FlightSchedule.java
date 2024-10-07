import java.util.List;
import java.util.stream.Collectors;

public class FlightSchedule {
    private List<Flight> flights;  // List of all available flights

    public FlightSchedule(List<Flight> flights) {
        this.flights = flights;
    }

    // Get flights departing from a specific city
    public List<Flight> getFlightsFrom(String departureCity) {
        return flights.stream()
                .filter(flight -> flight.getDepartureCity().equals(departureCity))
                .collect(Collectors.toList());
    }
}
