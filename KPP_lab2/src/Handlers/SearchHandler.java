package Handlers;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import Entities.*;

public class SearchHandler implements ActionListener {
    private final Flight[] flights;
    private final DefaultTableModel tableModel;
    private final JComboBox<String> departureComboBox;
    private final JComboBox<String> destinationComboBox;
    private final TableRowSorter sorter;

    public SearchHandler(Flight[] flights, DefaultTableModel tableModel, JComboBox<String> departureComboBox, JComboBox<String> destinationComboBox, TableRowSorter sorter) {
        this.flights = flights;
        this.tableModel = tableModel;
        this.departureComboBox = departureComboBox;
        this.destinationComboBox = destinationComboBox;
        this.sorter = sorter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Clear previous results
        tableModel.setRowCount(0);
        if (sorter != null) {
            sorter.setRowFilter(null); // Reset filters before performing search
        }

        // Get user input
        String departure = (String) departureComboBox.getSelectedItem();
        String destination = (String) destinationComboBox.getSelectedItem();

        // Use FlightSearch to find optimal routes
        FlightSearch flightSearch = new FlightSearch(List.of(flights)); // Convert array to List
        List<Route> possibleRoutes = flightSearch.findOptimalRoutes(departure, destination);
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        // Use a Set to track already added flights
        Set<String> addedFlights = new HashSet<>();

        for (Route route : possibleRoutes) {
            List<Flight> flightsInRoute = route.getFlights(); // Get flights from the route

            for (Flight flight : flightsInRoute) {
                // Create a unique key for each flight based on its attributes
                String flightKey = generateFlightKey(flight);

                // Check if the flight is already added
                if (!addedFlights.contains(flightKey)) {
                    // Format the times and add the flight to the table model
                    String displayPlaneType = flightsInRoute.size()>1 ? "Overlay" : flight.getPlaneType();
                    String[] row = {
                            displayPlaneType, // Assuming you want to show flight number
                            flight.getDepartureTime().format(dateTimeFormatter),
                            flight.getArrivalTime().format(dateTimeFormatter),
                            "$" + flight.getPrice(),
                            flight.getFormattedDuration() // Get the formatted duration
                    };
                    tableModel.addRow(row);

                    // Mark this flight as added
                    addedFlights.add(flightKey);
                }
            }
        }

        // If no routes were found, you might want to show a message or leave the table empty.
        if (possibleRoutes.isEmpty()) {
            System.out.println("No routes found.");
        }
    }

    // Helper method to generate a unique key for each flight based on its attributes
    private String generateFlightKey(Flight flight) {
        return flight.getFlightNumber() + "-" + flight.getDepartureTime() + "-" + flight.getArrivalTime();
    }
}
