package Handlers;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import Entities.*;

public class SearchHandler implements ActionListener {
    private final Flight[] flights;
    private final DefaultTableModel tableModel; // Adjust this import according to your setup
    private final JComboBox<String> departureComboBox; // Assuming you have a JComboBox for departures
    private final JComboBox<String> destinationComboBox; // Assuming you have a JComboBox for destinations

    public SearchHandler(Flight[] flights, DefaultTableModel tableModel, JComboBox<String> departureComboBox, JComboBox<String> destinationComboBox) {
        this.flights = flights;
        this.tableModel = tableModel;
        this.departureComboBox = departureComboBox;
        this.destinationComboBox = destinationComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Clear previous results
        tableModel.setRowCount(0);

        // Get user input
        String departure = (String) departureComboBox.getSelectedItem();
        String destination = (String) destinationComboBox.getSelectedItem();

        // Use Entities.FlightSearch to find optimal routes
        FlightSearch flightSearch = new FlightSearch(List.of(flights)); // Convert array to List
        List<Route> possibleRoutes = flightSearch.findOptimalRoutes(departure, destination);
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        for (Route route : possibleRoutes) {
            List<Flight> flightsInRoute = route.getFlights(); // Get flights from the route

            for (Flight flight : flightsInRoute) {
                // Format the times and add the flight to the table model
                String[] row = {
                        flight.getPlaneType(), // Assuming you want to show flight number
                        flight.getDepartureTime().format(dateTimeFormatter),
                        flight.getArrivalTime().format(dateTimeFormatter),
                        "$" + flight.getPrice(),
                        flight.getFormattedDuration() // Get the formatted duration
                };
                tableModel.addRow(row);
            }
        }

        // If no routes were found, you might want to show a message or leave the table empty.
        if (possibleRoutes.isEmpty()) {
            System.out.println("No routes found.");
        }
    }
}
