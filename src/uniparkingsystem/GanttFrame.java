package uniparkingsystem;

import javax.swing.JFrame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Date;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

public class GanttFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public GanttFrame() {

        setTitle("Parking Timeline");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        TaskSeriesCollection dataset = createDataset();

        JFreeChart chart = ChartFactory.createGanttChart( "University Parking Timeline", "Vehicle Plate", "Time",
        dataset, true, true, false);

        ChartPanel chartPanel = new ChartPanel(chart);

        	setContentPane(chartPanel);
        	setVisible(true);
    }

    private TaskSeriesCollection createDataset() {

        TaskSeries series = new TaskSeries("Parking Activity");

        try {

            Connection conn = DatabaseConnection.getConnection();

            String sql =
                    "SELECT plate, time_in, time_out " +
                    "FROM parking_report";

            PreparedStatement pst = conn.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {

                String plate = rs.getString("plate");

                Timestamp timeIn = rs.getTimestamp("time_in");

                Timestamp timeOut = rs.getTimestamp("time_out");

                if (timeIn == null) {
                    continue;
                }

                //if vehicle is still parked extend bar until current time
                
                if (timeOut == null) {

                    timeOut = new Timestamp(System.currentTimeMillis());
                }

                Date start = new Date(timeIn.getTime());

                Date end = new Date(timeOut.getTime());

                Task task = new Task(plate, start, end);

                series.add(task);
            }

            rs.close();
            pst.close();
            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        TaskSeriesCollection dataset = new TaskSeriesCollection();

        dataset.add(series);

        return dataset;
    }
}