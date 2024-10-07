import java.time.Duration;
import java.util.List;

public class Route {
    private List<Flight> flights;

    public Route(List<Flight> flights) {
        this.flights = flights;
    }

    public long getTotalTime() {
        long totalTime = 0;
        for (int i = 0; i < flights.size(); i++) {
            Flight flight = flights.get(i);
            totalTime += Duration.between(flight.getDepartureTime(), flight.getArrivalTime()).toMinutes();

            if (i < flights.size() - 1) {
                Flight nextFlight = flights.get(i + 1);
                totalTime += Duration.between(flight.getArrivalTime(), nextFlight.getDepartureTime()).toMinutes();
            }
        }
        return totalTime;
    }

    public double getTotalPrice() {
        return flights.stream().mapToDouble(Flight::getPrice).sum();
    }

    public List<Flight> getFlights() {
        return flights;
    }
}
