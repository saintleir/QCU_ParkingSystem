package uniparkingsystem;

import java.time.Duration;
import java.time.LocalDateTime;

public class Vehicle {

    private String owner;
    private String plateNumber;
    private String vehicleType;

    private LocalDateTime timeIn;
    private LocalDateTime timeOut;

    private boolean parked;

    public Vehicle(String owner, String plateNumber, String vehicleType) {

        this.owner = owner;
        this.plateNumber = plateNumber;
        this.vehicleType = vehicleType;

        this.timeIn = LocalDateTime.now();
        this.parked = true;
    }

    public void timeOut() {

        this.timeOut = LocalDateTime.now();
        this.parked = false;
    }

    public String getOwner() {
        return owner;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public LocalDateTime getTimeIn() {
        return timeIn;
    }

    public LocalDateTime getTimeOut() {
        return timeOut;
    }

    public boolean isParked() {
        return parked;
    }

    public String getDuration() {

        LocalDateTime end = (timeOut == null)
                ? LocalDateTime.now()
                : timeOut;

        Duration duration = Duration.between(timeIn, end);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        return hours + "h " + minutes + "m";
    }
}