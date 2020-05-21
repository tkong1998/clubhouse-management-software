package controller;

import java.util.*;
import java.io.*;
import java.time.Duration;
import java.time.LocalTime;

public class FacilityList {
    private ArrayList<Facility> facilityList;

    public FacilityList() {
        this.facilityList = new ArrayList<Facility>();
        String PATH = "data/facility.csv";
        File file = new File(PATH);

        // if file don't exist, then create the file and return
        if (!file.exists()) {
            String header = "facility_name, rent, open_hour, close_hour, capacity, session_duration, description";
            try (FileOutputStream outputFile = new FileOutputStream(PATH)) {
                outputFile.write(header.getBytes());
                outputFile.flush();
                outputFile.close();
            } catch (Exception exception) {
                exception.getStackTrace();
            }
            return;
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
                String name = lineData[0].trim();
                double rent = Double.parseDouble(lineData[1].trim());
                LocalTime oh = LocalTime.parse(lineData[2].trim());
                LocalTime ch = LocalTime.parse(lineData[3].trim());
                int capacity = Integer.parseInt(lineData[4].trim());
                Duration duration;
                if (lineData[5].trim().equals("null")){
                    duration = Duration.between(oh, ch);
                } else {
                    duration = Duration.ofMinutes(Long.parseLong(lineData[5].trim()));
                }
                
                String description = lineData[6].trim();
                Facility facility = new Facility(name, rent, oh, ch, capacity, duration, description);
                addFacility(facility);
            }
        } catch (Exception exception) {
            exception.getStackTrace();
        }

    }

    public ArrayList<Facility> getFacilityList(){
        return this.facilityList;
    }

    public Facility findFacility(String name){
        for (Facility facility: facilityList){
            if(facility.getFacility().equals(name)){
                return facility;
            }
        }
        return null;
    }

    public void addFacility(Facility facility){
        this.facilityList.add(facility);
    }

}