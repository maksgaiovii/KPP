import Entities.*;
import Handlers.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.format.FormatStyle;
import java.util.HashSet;
import java.util.Set;
import java.time.format.DateTimeFormatter;

public class FlightInfoSystemGUI extends JFrame {

    private JComboBox<String> departureComboBox;
    private JComboBox<String> destinationComboBox;
    private JSpinner dateSpinner;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    Flight[] flights= FlightGenerator.generateRandomFlights(20);

    public FlightInfoSystemGUI() {
        // Initialize the frame
        setTitle("Entities.Flight Information System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panels for inputs and results
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        JPanel resultPanel = new JPanel(new BorderLayout());

        // Components for departure and destination selection
        String[] uniqueCities = getUniqueCities();
        departureComboBox = new JComboBox<>(uniqueCities);
        destinationComboBox = new JComboBox<>(uniqueCities);

        // Date and time input
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy HH:mm");
        dateSpinner.setEditor(timeEditor);

        // Buttons for filtering and searching
        JButton searchButton = new JButton("Search");
        JButton filterButton = new JButton("Filter");

        // Add components to input panel
        inputPanel.add(new JLabel("Departure:"));
        inputPanel.add(departureComboBox);
        inputPanel.add(new JLabel("Destination:"));
        inputPanel.add(destinationComboBox);
        inputPanel.add(new JLabel("Date and Time:"));
        inputPanel.add(dateSpinner);
        inputPanel.add(searchButton);
        inputPanel.add(filterButton);

        // Table for showing results
        String[] columnNames = {"Entities.Flight", "Departure Time", "Arrival Time", "Price", "Duration"}; // Add Duration
        tableModel = new NonEditableTableModel(new Object[0][0], columnNames);
        resultsTable = new JTable(tableModel);

        // Add TableRowSorter for sorting functionality
        sorter = new TableRowSorter<>(tableModel);
        resultsTable.setRowSorter(sorter);

        // Add table to result panel
        resultPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        // Create an instance of SearchHandler
        SearchHandler searchHandler = new SearchHandler(flights, tableModel, departureComboBox, destinationComboBox);
        FilterHandler filterHandler = new FilterHandler(sorter);
        searchButton.addActionListener(searchHandler);
        filterButton.addActionListener(filterHandler);

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
        //loadAllFlights();
    }

    private String[] getUniqueCities() {
        Set<String> citySet = new HashSet<>();

        // Extract unique departure and arrival cities
        for (Flight flight : flights) {
            citySet.add(flight.getDepartureCity());
            citySet.add(flight.getArrivalCity());
        }

        return citySet.toArray(new String[0]); // Convert set to array
    }

    private void loadAllFlights() {
        tableModel.setRowCount(0); // Clear previous results
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

        for (Flight flight : flights) {
            String[] row = {
                    flight.getPlaneType(), // Assuming you want to show flight number
                    flight.getDepartureTime().format(dateTimeFormatter),
                    flight.getArrivalTime().format(dateTimeFormatter),
                    "$" + flight.getPrice()+ flight.getDuration()
            };
            tableModel.addRow(row);
        }
    }




    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlightInfoSystemGUI gui = new FlightInfoSystemGUI();
            gui.setVisible(true);
        });
    }
}
