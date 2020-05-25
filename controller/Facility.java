package controller;

import java.time.*;

public class Facility {
    private String name;
    private double rent;
    private LocalTime openHour;
    private LocalTime closeHour;
    private int capacity;
    private Duration sessionDuration;
    private String description;

    public Facility(String name, double rent, LocalTime openHour, LocalTime closeHour, int capacity, Duration duration,
            String description) {
        this.name = name;
        this.rent = rent;
        this.openHour = openHour;
        this.closeHour = closeHour;
        this.capacity = capacity;
        this.sessionDuration = duration;
        this.description = description;
    }

    public String getFacilityName() {
        return this.name;
    }

    public double getRent() {
        return this.rent;
    }

    public LocalTime getStartHour() {
        return this.openHour;
    }

    public LocalTime getCloseHour() {
        return this.closeHour;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public Duration getDuration() {
        return this.sessionDuration;
    }

    public String getDescription() {
        return this.description;
    }
}
