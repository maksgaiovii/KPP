package Handlers;

import javax.swing.*;
import javax.swing.RowFilter.Entry;
import javax.swing.event.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class FilterHandler implements ActionListener {
    private TableRowSorter<?> sorter; // Assuming you have a TableRowSorter instance
    private int priceColumnIndex = 3; // The index for the price column
    private int durationColumnIndex = 4; // The index for the duration column

    public FilterHandler(TableRowSorter<?> sorter) {
        this.sorter = sorter;
    }

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
                            String priceString = (String) entry.getValue(priceColumnIndex);
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
                            // Assuming duration is stored as a string like "1h 30m" or "90m"
                            String durationString = (String) entry.getValue(durationColumnIndex);
                            // Extract the numeric part for hours (e.g., "90m" -> 90 or "1h 30m" -> 1.5)
                            double durationHours = extractDurationInHours(durationString);
                            return durationHours <= maxDuration; // Compare with maxDuration
                        }

                        private double extractDurationInHours(String durationString) {
                            String[] parts = durationString.split(" ");
                            double hours = 0;
                            for (String part : parts) {
                                if (part.endsWith("h")) {
                                    hours += Double.parseDouble(part.replace("h", ""));
                                } else if (part.endsWith("m")) {
                                    hours += Double.parseDouble(part.replace("m", "")) / 60; // Convert minutes to hours
                                }
                            }
                            return hours;
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
