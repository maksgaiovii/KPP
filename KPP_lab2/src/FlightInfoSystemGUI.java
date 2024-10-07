import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FlightInfoSystemGUI extends JFrame {

    private JComboBox<String> departureComboBox;
    private JComboBox<String> destinationComboBox;
    private JSpinner dateSpinner;
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public FlightInfoSystemGUI() {
        // Initialize the frame
        setTitle("Flight Information System");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create panels for inputs and results
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        JPanel resultPanel = new JPanel(new BorderLayout());

        // Components for departure and destination selection
        departureComboBox = new JComboBox<>(new String[]{"New York", "London", "Paris", "Tokyo", "Sydney"});
        destinationComboBox = new JComboBox<>(new String[]{"New York", "London", "Paris", "Tokyo", "Sydney"});

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
        String[] columnNames = {"Flight", "Departure Time", "Arrival Time", "Price", "Duration"}; // Add Duration
        tableModel = new NonEditableTableModel(new Object[0][0], columnNames);
        resultsTable = new JTable(tableModel);

        // Add TableRowSorter for sorting functionality
        sorter = new TableRowSorter<>(tableModel);
        resultsTable.setRowSorter(sorter);

        // Add table to result panel
        resultPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);

        // Add action listeners for buttons
        searchButton.addActionListener(new SearchButtonListener());
        filterButton.addActionListener(new FilterButtonListener());

        // Add panels to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(resultPanel, BorderLayout.CENTER);
    }

    // ActionListener for search button
    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Clear previous results
            tableModel.setRowCount(0);
            sorter.setRowFilter(null);
            // Get user input
            String departure = (String) departureComboBox.getSelectedItem();
            String destination = (String) destinationComboBox.getSelectedItem();
            Object date = dateSpinner.getValue();

            // Format the date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            // Mock search results (in a real app, you'd query your data source)
            String[][] data = {
                    {"Flight 101", dateFormat.format(date), "10:00", "14:00", "$500", "4"},
                    {"Flight 202", dateFormat.format(date), "12:00", "16:00", "$450", "4"},
                    {"Flight 303", dateFormat.format(date), "15:00", "19:00", "$600", "4"},
            };

            // Add mock data to the table (real data would be fetched here)
            for (String[] row : data) {
                tableModel.addRow(row);
            }
        }
    }

    // ActionListener for filter button
    private class FilterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Create dialog to filter by price or duration
            JPanel panel = new JPanel(new GridLayout(2, 2));
            panel.add(new JLabel("Max Price:"));
            JTextField priceField = new JTextField();
            panel.add(priceField);
            panel.add(new JLabel("Max Duration (hours):"));
            JTextField durationField = new JTextField();
            panel.add(durationField);

            int result = JOptionPane.showConfirmDialog(null, panel,
                    "Enter Price and Duration Filters", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String priceText = priceField.getText().trim();
                    String durationText = durationField.getText().trim();

                    List<RowFilter<Object, Object>> filters = new ArrayList<>();

                    // If price is entered, apply price filter
                    if (!priceText.isEmpty()) {
                        double maxPrice = Double.parseDouble(priceText.replace("$", ""));
                        RowFilter<Object, Object> priceFilter = new RowFilter<Object, Object>() {
                            @Override
                            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                                String priceString = (String) entry.getValue(3);
                                double price = Double.parseDouble(priceString.replace("$", ""));
                                return price <= maxPrice;
                            }
                        };
                        filters.add(priceFilter);
                    }

                    // If duration is entered, apply duration filter
                    if (!durationText.isEmpty()) {
                        double maxDuration = Double.parseDouble(durationText);
                        RowFilter<Object, Object> durationFilter = new RowFilter<Object, Object>() {
                            @Override
                            public boolean include(Entry<? extends Object, ? extends Object> entry) {
                                String durationString = (String) entry.getValue(4); // Assuming duration is in hours
                                double duration = Double.parseDouble(durationString);
                                return duration <= maxDuration;
                            }
                        };
                        filters.add(durationFilter);
                    }

                    // Apply the filters (if any were added)
                    if (!filters.isEmpty()) {
                        sorter.setRowFilter(RowFilter.andFilter(filters));
                    } else {
                        sorter.setRowFilter(null); // Clear filters if none are applied
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid price or duration format.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FlightInfoSystemGUI gui = new FlightInfoSystemGUI();
            gui.setVisible(true);
        });
    }
}
