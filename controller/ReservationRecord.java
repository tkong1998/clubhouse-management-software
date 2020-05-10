package controller;

import java.util.*;

public class ReservationRecord {

    private ArrayList<Reservation> reservationRecord = new ArrayList<Reservation>();

    public ArrayList<Reservation> getRecords(){
        return this.reservationRecord;
    }

    public int getSize(){
        return this.reservationRecord.size();
    }

    public List<Facility> getThreeMostPopularFacility(){
        Map<Facility,Integer> mp = new HashMap<>();
        for (Reservation reservation: this.reservationRecord){
            if (mp.containsKey(reservation.getFacility())){ 
                mp.put(reservation.getFacility(), mp.get(reservation.getFacility())+1); 
            } else { 
                mp.put(reservation.getFacility(),1); 
            } 
        }
        PriorityQueue<Map.Entry<Facility,Integer>> queue = new PriorityQueue<>(Comparator.comparing(e -> e.getValue()));
        for (Map.Entry<Facility,Integer> facility : mp.entrySet()){
            queue.add(facility);
            if (queue.size() > 3){
                queue.poll();
            }
        }
        ArrayList<Facility> result = new ArrayList<Facility>();
        while (queue.size() > 0) {
            result.add(queue.poll().getKey());
        }
        Collections.reverse(result);
        return result;
    }


}