package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class FileLoader {
    private final String FACILITY_PATH = "data/facility.csv";
    private final String MEMBER_PATH = "data/member.csv";
    private final String RESERVATION_PATH = "data/reservation.csv";
    private final String STAFF_PATH = "data/staff.csv";

    private final String[] PATHS = { FACILITY_PATH, MEMBER_PATH, RESERVATION_PATH, STAFF_PATH };

    private ArrayList<Facility> facilityList;
    private ArrayList<Manager> managerList;
    private ArrayList<Member> memberList;
    private ArrayList<Reservation> reservationList;
    private ArrayList<Staff> staffList;

    public FileLoader() {
        facilityList = new ArrayList<Facility>();
        managerList = new ArrayList<Manager>();
        memberList = new ArrayList<Member>();
        reservationList = new ArrayList<Reservation>();
        staffList = new ArrayList<Staff>();

        for (String path : PATHS) {
            File file = new File(path);
            // if file don't exist, then create the file and return
            if (!file.exists()) {
                String header = "";
                if (path.equals(FACILITY_PATH)) {
                    header = "facility_name, rent, open_hour, close_hour, capacity, session_duration, description";
                } else if (path.equals(MEMBER_PATH)) {
                    header = "member_id, name, address, email, phone";
                } else if (path.equals(RESERVATION_PATH)) {
                    header = "member_id, booked_facility, date, start_time, end_time, status";
                } else if (path.equals(STAFF_PATH)) {
                    header = "staff_id, name, password, role";
                }
                try (FileOutputStream outputFile = new FileOutputStream(path)) {
                    outputFile.write(header.getBytes());
                    outputFile.flush();
                    outputFile.close();
                } catch (Exception exception) {
                    exception.getStackTrace();
                }
                continue;
            }
            // load the facility list
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
                    if (path.equals(FACILITY_PATH)) {
                        String name = lineData[0].trim();
                        double rent = Double.parseDouble(lineData[1].trim());
                        LocalTime oh = LocalTime.parse(lineData[2].trim());
                        LocalTime ch = LocalTime.parse(lineData[3].trim());
                        int capacity = Integer.parseInt(lineData[4].trim());
                        Duration duration;
                        if (lineData[5].trim().equals("null")) {
                            duration = Duration.between(oh, ch);
                        } else {
                            duration = Duration.ofMinutes(Long.parseLong(lineData[5].trim()));
                        }
                        String description = lineData[6].trim();

                        Facility facility = new Facility(name, rent, oh, ch, capacity, duration, description);
                        facilityList.add(facility);

                    } else if (path.equals(MEMBER_PATH)) {
                        String memberID = lineData[0].trim();
                        String name = lineData[1].trim();
                        String address = lineData[2].trim();
                        String email = lineData[3].trim();
                        String phone = lineData[4].trim();

                        Member member = new Member(memberID, name, address, email, phone);
                        memberList.add(member);

                    } else if (path.equals(RESERVATION_PATH)) {
                        String memberID = lineData[0].trim();
                        Member member = findMember(memberID);
                        String facilityName = lineData[1].trim();
                        Facility facility = findFacility(facilityName);
                        LocalDate date = LocalDate.parse(lineData[2].trim());
                        LocalTime start = LocalTime.parse(lineData[3].trim());
                        LocalTime end = LocalTime.parse(lineData[4].trim());
                        String status = lineData[5].trim();

                        Reservation reservation = new Reservation(member, facility, date, start, end, status);
                        reservationList.add(reservation);

                    } else if (path.equals(STAFF_PATH)) {
                        String staffID = lineData[0].trim();
                        String name = lineData[1].trim();
                        String password = lineData[2].trim();
                        String role = lineData[3].trim();

                        if (role.equals("staff")) {
                            staffList.add(new Staff(name, staffID, password));
                        } else if (role.equals("manager")) {
                            managerList.add(new Manager(name, staffID, password));
                        }
                    }
                }

            } catch (Exception exception) {
                exception.getStackTrace();
            }
        }
    }

    public Member findMember(String id) {
        for (Member member : memberList) {
            if (member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }

    public Facility findFacility(String name) {
        for (Facility facility : facilityList) {
            if (facility.getFacility().equals(name)) {
                return facility;
            }
        }
        return null;
    }

    public ArrayList<Facility> getFacilities() {
        return this.facilityList;
    }

    public ArrayList<Member> getmembers() {
        return this.memberList;
    }

    public ArrayList<Reservation> getReservations() {
        return this.reservationList;
    }

    public ArrayList<Staff> getStaffs() {
        return this.staffList;
    }

    public ArrayList<Manager> getManagers() {
        return this.managerList;
    }

    public void writeRecords() {
        try {
            FileWriter file = new FileWriter(RESERVATION_PATH);
            file.write("member_id,booked_facility,date,start_time,end_time,status\n");
            for (Reservation reservation : reservationList) {
                file.write(reservation.getMember().getId() + ", ");
                file.write(reservation.getFacility().getFacility() + ", ");
                file.write(reservation.getDate().toString() + ", ");
                file.write(reservation.getStart().toString() + ", ");
                file.write(reservation.getEnd().toString() + ", ");
                file.write(reservation.getStatus() + "\n");
            }
            file.close();
            System.out.println("Write success to " + RESERVATION_PATH);
        } catch (Exception e) {
            e.getStackTrace();
        }

    }
}