package Entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class FlightGenerator {
    private static final String[] CITIES = {"New York", "London", "Paris", "Tokyo", "Sydney"};
    private static final String[] PLANE_TYPES = {"Boeing 747", "Airbus A380", "Cessna 172", "Embraer 175", "Boeing 737"};
    private static final Random RANDOM = new Random();
    private static int flightCounter = 100; // Entities.Flight number starting point

    // DateTimeFormatter for generating formatted flight times
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static Flight generateRandomFlight() {
        // Generate a flight number
        String flightNumber = "Fl " + flightCounter++;

        // Select random departure and arrival cities
        String departureCity = CITIES[RANDOM.nextInt(CITIES.length)];
        String arrivalCity;
        do {
            arrivalCity = CITIES[RANDOM.nextInt(CITIES.length)];
        } while (arrivalCity.equals(departureCity)); // Ensure arrival city is different from departure city

        // Generate random departure time within the next month
        LocalDateTime departureTime = LocalDateTime.now().plusDays(RANDOM.nextInt(30)).plusHours(RANDOM.nextInt(24));
        // Generate arrival time that is at least 1 hour later than departure time
        LocalDateTime arrivalTime = departureTime.plusHours(RANDOM.nextInt(12)).plusMinutes(RANDOM.nextInt(59));

        // Generate random price between $100 and $1000
        int price = 100 + RANDOM.nextInt(1200);

        // Select random plane type
        String planeType = PLANE_TYPES[RANDOM.nextInt(PLANE_TYPES.length)];

        // Create and return the Entities.Flight object
        return new Flight(flightNumber, departureCity, arrivalCity,
                departureTime.format(formatter),
                arrivalTime.format(formatter),
                price,
                planeType);
    }
    public static Flight[] generateRandomFlights(int amount){
        ArrayList<Flight> flights=new ArrayList<>();
        for(int i=0;i<amount;i++){
            flights.add(generateRandomFlight());
        }
        return flights.toArray(new Flight[0]);
    }


}
