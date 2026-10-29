package uniparkingsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportsFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable table;
    private DefaultTableModel model;

    public ReportsFrame() {

        setTitle("Parking Reports");
        setSize(900, 450);
        setLocationRelativeTo(null);

        String[] columns = {"Owner", "Plate", "Vehicle", "Time In", "Time Out", "Duration", "Status"};

        model = new DefaultTableModel(columns, 0);

        table = new JTable(model);

        add(new JScrollPane(table), BorderLayout.CENTER);

        loadRecords();

        setVisible(true);
    }

    private void loadRecords() {

        model.setRowCount(0);

        String sql = "SELECT * FROM parking_report";

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {model.addRow(new Object[]{

            		rs.getString("owner"),
            		rs.getString("plate"),
            		rs.getString("vehicle"),
            		rs.getTimestamp("time_in"),
            		rs.getTimestamp("time_out"),
            		rs.getString("duration"),
            		rs.getString("status")
                });
            }

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error loading records from database.");
        }
    }
}