package Entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {
    private String flightNumber;  // Entities.Flight number
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private int price;
    private String planeType;
    private Duration duration;  // To store flight duration

    // DateTimeFormatter to parse and format time strings
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight(String flightNumber, String departureCity, String arrivalCity, String departureTime, String arrivalTime, int price, String planeType) {
        this.flightNumber = flightNumber;  // Initialize flight number
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = LocalDateTime.parse(departureTime, formatter); // Parsing string to LocalDateTime
        this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter);     // Parsing string to LocalDateTime
        this.price = price;
        this.planeType = planeType;

        // Calculate and store the duration
        this.duration = Duration.between(this.departureTime, this.arrivalTime);
    }

    // Method to get the duration in a human-readable format (hours and minutes)
    public String getFormattedDuration() {
        var s=getDuration();
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        return hours + "h " + minutes + "m";
    }

    // Getters and Setters
    public String getFlightNumber() { return flightNumber; } // Getter for flight number
    public void setFlightNumber(String flightNumber) { this.flightNumber = flightNumber; }

    public String getDepartureCity() { return departureCity; }
    public void setDepartureCity(String departureCity) { this.departureCity = departureCity; }

    public String getArrivalCity() { return arrivalCity; }
    public void setArrivalCity(String arrivalCity) { this.arrivalCity = arrivalCity; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = LocalDateTime.parse(departureTime, formatter); }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter); }

    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }

    public String getPlaneType() { return planeType; }
    public void setPlaneType(String planeType) { this.planeType = planeType; }

    public Duration getDuration() {
            duration=Duration.between(this.departureTime, this.arrivalTime);
            return duration; }
}
