package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class SearchButtonListener implements ActionListener {
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