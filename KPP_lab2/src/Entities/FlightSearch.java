package Entities;

import java.util.*;

public class FlightSearch {
    private List<Flight> flights;

    public FlightSearch(List<Flight> flights) {
        this.flights = flights;
    }

    public List<Route> findOptimalRoutes(String departureCity, String arrivalCity) {
        List<Route> possibleRoutes = new ArrayList<>();

        // Filter flights by departure city
        List<Flight> initialFlights = flights.stream()
                .filter(flight -> flight.getDepartureCity().equals(departureCity))
                .toList();

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
            List<Flight> connectingFlights = flights.stream()
                    .filter(flight -> flight.getDepartureCity().equals(currentFlight.getArrivalCity()))
                    .toList();

            for (Flight nextFlight : connectingFlights) {
                if (nextFlight.getDepartureTime().isAfter(currentFlight.getArrivalTime())) {
                    findRoutesRecursive(nextFlight, arrivalCity, new ArrayList<>(currentRoute), possibleRoutes);
                }
            }
        }
    }
}
