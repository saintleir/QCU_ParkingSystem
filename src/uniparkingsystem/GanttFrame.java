package uniparkingsystem;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GanttFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public GanttFrame() {

        setTitle("Parking Timeline");
        setSize(700, 500);
        setLocationRelativeTo(null);

        JTextArea area =
                new JTextArea();

        area.setEditable(false);

        area.setFont(
                new Font(
                        "Monospaced",
                        Font.PLAIN,
                        14));

        area.append(
                "PARKING ACTIVITY TIMELINE\n\n");

        loadTimeline(area);

        add(new JScrollPane(area));

        setVisible(true);
    }

    private void loadTimeline(
            JTextArea area) {

        String sql =
                "SELECT * FROM parking_report";

        try {

            Connection conn =
                    DatabaseConnection.getConnection();

            PreparedStatement pst =
                    conn.prepareStatement(sql);

            ResultSet rs =
                    pst.executeQuery();

            while(rs.next()) {

                String plate =
                        rs.getString("plate");

                String duration =
                        rs.getString("duration");

                if(duration == null) {

                    duration = "0h 0m";
                }

                String[] parts =
                        duration.split("[hm ]+");

                int hours =
                        Integer.parseInt(parts[0]);

                int minutes =
                        Integer.parseInt(parts[1]);

                int totalMinutes =
                        (hours * 60) + minutes;

                // 1 block = 2 minutes
                int blocks =
                        Math.max(1,
                                totalMinutes / 2);

                StringBuilder bars =
                        new StringBuilder();

                for(int j = 0;
                    j < blocks;
                    j++) {

                    bars.append("█");
                }

                area.append(
                        String.format(
                                "%-12s %-30s %s%n",
                                plate,
                                bars.toString(),
                                duration));
            }

            conn.close();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }
}