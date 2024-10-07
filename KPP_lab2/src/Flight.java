import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Flight {
    private String departureCity;
    private String arrivalCity;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private double price;
    private String planeType;

    // DateTimeFormatter to parse and format time strings
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public Flight(String departureCity, String arrivalCity, String departureTime, String arrivalTime, double price, String planeType) {
        this.departureCity = departureCity;
        this.arrivalCity = arrivalCity;
        this.departureTime = LocalDateTime.parse(departureTime, formatter); // Parsing string to LocalDateTime
        this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter);     // Parsing string to LocalDateTime
        this.price = price;
        this.planeType = planeType;
    }

    // Getters and Setters
    public String getDepartureCity() { return departureCity; }
    public void setDepartureCity(String departureCity) { this.departureCity = departureCity; }

    public String getArrivalCity() { return arrivalCity; }
    public void setArrivalCity(String arrivalCity) { this.arrivalCity = arrivalCity; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(String departureTime) { this.departureTime = LocalDateTime.parse(departureTime, formatter); }

    public LocalDateTime getArrivalTime() { return arrivalTime; }
    public void setArrivalTime(String arrivalTime) { this.arrivalTime = LocalDateTime.parse(arrivalTime, formatter); }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getPlaneType() { return planeType; }
    public void setPlaneType(String planeType) { this.planeType = planeType; }
}
