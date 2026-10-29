package uniparkingsystem;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.ResultSet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class DashboardFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtOwner;
    private JTextField txtPlate;

    private JComboBox<String> cmbVehicle;

    private JRadioButton rbTimeIn;
    private JRadioButton rbTimeOut;

    private final int totalCars = 30;
    private final int totalMotorcycles = 100;

    private int availableCars = totalCars;
    private int availableMotorcycles = totalMotorcycles;

    public DashboardFrame() {

        setTitle("QCU Parking System");
        setSize(550, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        getContentPane().setBackground(new Color(33, 104, 187));

        JLabel title = new JLabel("QUEZON CITY UNIVERSITY", SwingConstants.CENTER);

        	title.setForeground(Color.WHITE);
        	title.setFont(new Font("Arial", Font.BOLD, 24));
        	title.setBounds(100, 40, 330, 35);
        	add(title);

        JLabel subtitle = new JLabel("PARKING MANAGEMENT SYSTEM", SwingConstants.CENTER);

        	subtitle.setForeground(Color.WHITE);
        	subtitle.setBounds(100, 70, 330, 20);
        	add(subtitle);

        JLabel lblOwner = new JLabel("Student ID / Owner Name");

        	lblOwner.setForeground(Color.WHITE);
        	lblOwner.setBounds(70, 130, 250, 20);
        	add(lblOwner);

        txtOwner = new JTextField();

        	txtOwner.setBounds(70, 155, 380, 35);
        	addPlaceholder(txtOwner, "Student ID / Owner Name");
        	add(txtOwner);

        JLabel lblPlate = new JLabel("Vehicle Plate Number");

        	lblPlate.setForeground(Color.WHITE);
        	lblPlate.setBounds(70, 210, 250, 20);
        	add(lblPlate);

        txtPlate = new JTextField();

        	txtPlate.setBounds(70, 235, 380, 35);
        	addPlaceholder(txtPlate, "Vehicle Plate Number");
        	add(txtPlate);

        JLabel lblVehicle = new JLabel("Vehicle Type");

        	lblVehicle.setForeground(Color.WHITE);
        	lblVehicle.setBounds(70, 290, 250, 20);
        	add(lblVehicle);

        cmbVehicle = new JComboBox<>(new String[]{"Car", "Motorcycle"});

        	cmbVehicle.setBounds(70, 315, 380, 35);
        	add(cmbVehicle);

        rbTimeIn = new JRadioButton("TIME IN", true);

        	rbTimeIn.setOpaque(false);
        	rbTimeIn.setForeground(Color.WHITE);
        
        rbTimeOut = new JRadioButton("TIME OUT");

        	rbTimeOut.setOpaque(false);
        	rbTimeOut.setForeground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();

        	group.add(rbTimeIn);
        		rbTimeIn.setBounds(120, 370, 100, 30);
        	group.add(rbTimeOut);
        		rbTimeOut.setBounds(250, 370, 120, 30);
        
        	add(rbTimeIn);
        	add(rbTimeOut);

        JButton btnSubmit = new JButton("SUBMIT");

        	btnSubmit.setBounds(70, 430, 380, 45);
        	add(btnSubmit);

        JButton btnReports = new JButton("REPORTS");

        	btnReports.setBounds(70, 500, 180, 35);
        	add(btnReports);

        JButton btnTimeline = new JButton("TIMELINE");

        	btnTimeline.setBounds(270, 500, 180, 35);
        	add(btnTimeline);

        	
// action here
        	
        btnSubmit.addActionListener(new ActionListener() {

             
             public void actionPerformed(ActionEvent e) {
                processTransaction();
                }
        });

        btnReports.addActionListener(new ActionListener() {
 
             public void actionPerformed(ActionEvent e) {
                new ReportsFrame();
                }
        });

        btnTimeline.addActionListener(new ActionListener() {

             public void actionPerformed(ActionEvent e) {
            	new GanttFrame();
                }
        });
        
        addWindowListener(
                new java.awt.event.WindowAdapter() {

                public void windowClosing(java.awt.event.WindowEvent e) {

                   autoExitAllVehicles();
                }
        });
        
        setVisible(true);
    }
    
    // methods

    private void processTransaction() {

        String owner = txtOwner.getText().trim();

        String plate = txtPlate.getText().trim().toUpperCase();

        if(plate.isEmpty() || plate.equals("Vehicle Plate Number")) {

            JOptionPane.showMessageDialog(this, "Enter a plate number.");

            return;
        }

        if(rbTimeIn.isSelected()) {

            if(owner.isEmpty() || owner.equals("Student ID / Owner Name")) {

                JOptionPane.showMessageDialog(this, "Enter a Student ID or Owner Name.");

                return;
            }

            boolean student = owner.matches("\\d{2}-\\d{4}");

            boolean personnel = owner.matches("[A-Za-z ]{3,}");

            	if(!student && !personnel) {

            		JOptionPane.showMessageDialog(this, "Invalid Student ID.\nExample: 26-0425");

            		return;
            	}

            String vehicleType = (String) cmbVehicle.getSelectedItem();

            	if(isVehicleAlreadyParked(plate)) {

            		JOptionPane.showMessageDialog(this, "Vehicle already parked.");

            		return;
            	}

        if(vehicleType.equals("Car")) {

            if(availableCars == 0) {

                JOptionPane.showMessageDialog(this, "Car Parking Full");

                return;
            }

                availableCars--;

            } else {

                if(availableMotorcycles == 0) {

                    JOptionPane.showMessageDialog(this, "Motorcycle Parking Full");

                    return;
                }

                availableMotorcycles--;
            }

            saveTimeInToDatabase( owner, plate, vehicleType);

            showParkingReport(owner, plate, "TIME IN SUCCESSFUL");

            resetForm();

        } else {

            processTimeOutFromDatabase(plate);

            resetForm();
        }
    }
    
    private boolean isVehicleAlreadyParked(String plate) {

        String sql =
                "SELECT * FROM parking_report " +
                "WHERE plate = ? " +
                "AND status = 'PARKED'";

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, plate);

            ResultSet rs = pst.executeQuery();

            boolean found = rs.next();

            conn.close();

            return found;

        } catch(Exception e) {

            e.printStackTrace();

            return false;
        }
    }
    
    
    private void saveTimeInToDatabase(String owner, String plate, String vehicleType) {

        String sql =
                "INSERT INTO parking_report " +
                "(owner, plate, vehicle, time_in, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, owner);
            pst.setString(2, plate);
            pst.setString(3, vehicleType);

            pst.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            pst.setString(5, "PARKED");

            pst.executeUpdate();

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    private void updateTimeOutInDatabase(String plate, String duration) {

    	String sql =
    	        "UPDATE parking_report " +
    	        "SET time_out=?, duration=?, status=? " +
    	        "WHERE plate=? AND status='PARKED'";

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setTimestamp(1, new Timestamp(System.currentTimeMillis()));

            pst.setString(2, duration);

            pst.setString(3, "EXITED");

            pst.setString(4, plate);

            pst.executeUpdate();

            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    private void processTimeOutFromDatabase(
            String plate) {

        String searchSql =
                "SELECT * " +
                "FROM parking_records " +
                "WHERE plate = ? " +
                "AND status = 'PARKED'";

        try {

            Connection conn = DatabaseConnection.getConnection();

            PreparedStatement pst = conn.prepareStatement(searchSql);

            pst.setString(1, plate);

            ResultSet rs = pst.executeQuery();

            if(!rs.next()) {

                JOptionPane.showMessageDialog(this, "Plate Number Not Found");

                conn.close();

                return;
            }

            String vehicleType = rs.getString("vehicle");

            Timestamp timeIn = rs.getTimestamp("time_in");

            Timestamp timeOut = new Timestamp(System.currentTimeMillis());

            long millis = timeOut.getTime() - timeIn.getTime();

            long hours = millis / (1000 * 60 * 60);

            long minutes = (millis / (1000 * 60)) % 60;

            String duration = hours + "h " + minutes + "m";

            updateTimeOutInDatabase(plate, duration);

            if(vehicleType.equals("Car")) {

                availableCars++;

            } else {

                availableMotorcycles++;
            }

            JOptionPane.showMessageDialog(this, "TIME OUT SUCCESSFUL\n\n" + "Duration: " + duration);

            conn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
    

    private void showParkingReport(String owner, String plate, String transaction) {

        int occupiedCars = totalCars - availableCars;

        int occupiedMotor = totalMotorcycles - availableMotorcycles;

        int occupied = occupiedCars + occupiedMotor;

        int total = totalCars + totalMotorcycles;

        double occupancy = occupied * 100.0 / total;

        String status;

        if(occupancy >= 90) {

            status = "HIGH CONGESTION ALERT";

        } else if(occupancy >= 75) {

            status = "WARNING";

        } else {

            status = "NORMAL";
        }

        String report =

                transaction +

                "\n\nOwner: " + owner +

                "\nPlate: " + plate +

                "\n\nCAR AVAILABLE: " + availableCars + "/" + totalCars +

                "\nMOTORCYCLE AVAILABLE: " + availableMotorcycles + "/" + totalMotorcycles +

                "\n\nOccupancy: " + String.format("%.2f%%", occupancy) +

                "\nStatus: " + status;

        JOptionPane.showMessageDialog(this, report, "Parking Availability Report", occupancy >= 90
                        ? JOptionPane.WARNING_MESSAGE
                        : JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void autoExitAllVehicles() {

        try {

            Connection conn = DatabaseConnection.getConnection();

            String selectSql =
                    "SELECT id, plate, time_in " +
                    "FROM parking_report " +
                    "WHERE status='PARKED'";

            PreparedStatement selectPst = conn.prepareStatement(selectSql);

            ResultSet rs = selectPst.executeQuery();

            while(rs.next()) {

                int id = rs.getInt("id");

                Timestamp timeIn = rs.getTimestamp("time_in");

                Timestamp timeOut = new Timestamp(
                                System.currentTimeMillis());

                long milliseconds = timeOut.getTime() - timeIn.getTime();

                long hours = milliseconds / (1000 * 60 * 60);

                long minutes = (milliseconds / (1000 * 60)) % 60;

                String duration = hours + "h " + minutes + "m";

                String updateSql =
                        "UPDATE parking_report " +
                        "SET time_out=?, " +
                        "duration=?, " +
                        "status='EXITED' " +
                        "WHERE id=?";

                PreparedStatement updatePst = conn.prepareStatement(updateSql);

                updatePst.setTimestamp(1, timeOut);

                updatePst.setString(2, duration);

                updatePst.setInt(3, id);

                updatePst.executeUpdate();

                updatePst.close();
            }

            rs.close();
            selectPst.close();
            conn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    private void resetForm() {

        txtOwner.setText("Student ID / Owner Name");

        txtOwner.setForeground(Color.GRAY);

        txtPlate.setText("Vehicle Plate Number");

        txtPlate.setForeground(Color.GRAY);

        cmbVehicle.setSelectedIndex(0);

        rbTimeIn.setSelected(true);

        txtOwner.requestFocus();
    }

    private void addPlaceholder(JTextField field, String placeholder) {

        field.setText(placeholder);

        field.setForeground(Color.GRAY);

        field.addFocusListener(new FocusAdapter() {

        @Override
        public void focusGained(FocusEvent e) {

              if(field.getText().equals(placeholder)) {

                  field.setText("");

                  field.setForeground(Color.BLACK);
              }
        }

        @Override
        public void focusLost(FocusEvent e) {

              if(field.getText().trim().isEmpty()) {

                  field.setText(placeholder);

                  field.setForeground(Color.GRAY);
              }
        }
     });
    }

    public static void main(
            String[] args) {

        SwingUtilities.invokeLater(
                DashboardFrame::new);
    }
}