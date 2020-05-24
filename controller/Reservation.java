package controller;

import java.time.*;

public class Reservation {
    private Member member;
    private Facility facility;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private long duration;
    private String status;

    public Reservation(Member member, Facility facility, LocalDate date, LocalTime start, LocalTime end, String status){
        this.member = member;
        this.facility = facility;
        this.date = date;
        this.startTime = start;
        this.endTime = end;
        this.status = status;
        this.duration = Duration.between(this.startTime,this.endTime).toMinutes();
    }

    public Member getMember(){
        return this.member;
    }

    public Facility getFacility(){
        return this.facility;
    }

    public LocalDate getDate(){
        return this.date;
    }

    public LocalTime getStart(){
        return this.startTime;
    }

    public LocalTime getEnd(){
        return this.endTime;
    }

    public long getDuration(){
        return this.duration;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}