package controller;

import java.io.*;
import java.util.*;
import java.time.*;

public class ReservationRecord {

    private ArrayList<Reservation> reservationRecord;
    private final String PATH = "./data/reservation.csv";

    public ReservationRecord() {
        reservationRecord = new ArrayList<Reservation>();
        File file = new File(PATH);

        // if file don't exist, then create the file and return
        if (!file.exists()) {
            String header = "member_id,booked_facility,date,start_time,end_time,duration,status";
            try (FileOutputStream outputFile = new FileOutputStream(PATH)) {
                outputFile.write(header.getBytes());
                outputFile.flush();
                outputFile.close();
            } catch (Exception exception) {
                exception.getStackTrace();
            }
            return;
        }
        // load the reservation record
        MemberList memberList = new MemberList();
        FacilityList facilityList = new FacilityList();

        try (Scanner input = new Scanner(file)) {
            String line, lineData[];
            for (int i = 0; input.hasNextLine(); i++) {
                line = input.nextLine();
                // skip the first line (header)
                if (i == 0) {
                    continue;
                }
                // skip the empty line
                if (line.trim().isEmpty()) {
                    continue;
                }
                lineData = line.split(",");
                String memberID = lineData[0].trim();
                Member member = memberList.findMember(memberID);
                String facilityName = lineData[1].trim();
                Facility facility = facilityList.findFacility(facilityName);
                LocalDate date = LocalDate.parse(lineData[2].trim());
                LocalTime start = LocalTime.parse(lineData[3].trim());
                LocalTime end = LocalTime.parse(lineData[4].trim());
                
                // long duration = Long.parseLong(lineData[5]);
                String status = lineData[6].trim();
                Reservation reservation = new Reservation(member, facility, date, start, end, status);
                addRecord(reservation);
            }
        } catch (Exception exception) {
            exception.getStackTrace();
        }
    }

    public ArrayList<Reservation> getRecords() {
        return this.reservationRecord;
    }

    public int getSize() {
        return this.reservationRecord.size();
    }

    public List<Facility> getThreeMostPopularFacility() {
        Map<Facility, Integer> mp = new HashMap<>();
        for (Reservation reservation : this.reservationRecord) {
            if (mp.containsKey(reservation.getFacility())) {
                mp.put(reservation.getFacility(), mp.get(reservation.getFacility()) + 1);
            } else {
                mp.put(reservation.getFacility(), 1);
            }
        }
        PriorityQueue<Map.Entry<Facility, Integer>> queue = new PriorityQueue<>(
                Comparator.comparing(e -> e.getValue()));
        for (Map.Entry<Facility, Integer> facility : mp.entrySet()) {
            queue.add(facility);
            if (queue.size() > 3) {
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

    public void addRecord(Reservation reservation) {
        this.reservationRecord.add(reservation);
    }

    public void writeRecord() {
        try {
            FileWriter file = new FileWriter(PATH);
            file.write("member_id,booked_facility,date,start_time,end_time,duration,status\n");
            for (Reservation reservation : reservationRecord) {
                file.write(reservation.getMember().getMemberID() + ", ");
                file.write(reservation.getFacility().getFacility() + ", ");
                file.write(reservation.getDate().toString() + ", ");
                file.write(reservation.getStart().toString() + ", ");
                file.write(reservation.getEnd().toString() + ", ");
                file.write(reservation.getDuration() + ", ");
                file.write(reservation.getStatus() + "\n");
            }
            file.close();
            System.out.println("Write success to " + PATH);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }

}