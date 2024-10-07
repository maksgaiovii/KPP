import java.time.Duration;
import java.util.*;

public class FlightSearch {
    private FlightSchedule flightSchedule;

    public FlightSearch(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    public List<Route> findOptimalRoutes(String departureCity, String arrivalCity) {
        List<Route> possibleRoutes = new ArrayList<>();

        List<Flight> initialFlights = flightSchedule.getFlightsFrom(departureCity);

        for (Flight flight : initialFlights) {
            findRoutesRecursive(flight, arrivalCity, new ArrayList<>(), possibleRoutes);
        }

        possibleRoutes.sort(Comparator
                .comparingLong(Route::getTotalTime)
                .thenComparingDouble(Route::getTotalPrice));

        return possibleRoutes;
    }

    private void findRoutesRecursive(Flight currentFlight, String arrivalCity, List<Flight> currentRoute, List<Route> possibleRoutes) {
        currentRoute.add(currentFlight);

        if (currentFlight.getArrivalCity().equals(arrivalCity)) {
            possibleRoutes.add(new Route(new ArrayList<>(currentRoute)));
        } else {
            List<Flight> connectingFlights = flightSchedule.getFlightsFrom(currentFlight.getArrivalCity());

            for (Flight nextFlight : connectingFlights) {
                if (nextFlight.getDepartureTime().isAfter(currentFlight.getArrivalTime())) {
                    findRoutesRecursive(nextFlight, arrivalCity, new ArrayList<>(currentRoute), possibleRoutes);
                }
            }
        }
    }
}
