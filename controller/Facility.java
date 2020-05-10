package controller;

import java.time.*;
import java.util.ArrayList;

public class Facility {
    private String name;
    private double rent;
    private LocalTime openHour;
    private LocalTime closeHour;
    private int capacity;
    private Duration sessionDuration;
    private String description;
    private ArrayList<Reservation> reservations = new ArrayList<Reservation>();

    public Facility(String name, double rent, LocalTime oh, LocalTime ch, int cap, Duration duration, String description){
        this.name = name;
        this.rent = rent;
        this.openHour = oh;
        this.closeHour = ch;
        this.capacity = cap;
        this.sessionDuration = duration;
        this.description = description;
    }

    public String getFacility(){
        return this.name;
    }

    public double getRent(){
        return this.rent;
    }

    public LocalTime getStartHour(){
        return this.openHour;
    }

    public LocalTime getCloseHour(){
        return this.closeHour;
    }

    public int getCapacity(){
        return this.capacity;
    }
    public Duration getDuration(){
        return this.sessionDuration;
    }

    public String getDescription(){
        return this.description;
    }

    public ArrayList<Reservation> getReservations(){
        return this.reservations;
    }

    public void addReservation(Reservation reservation){
        this.reservations.add(reservation);
    }
}
