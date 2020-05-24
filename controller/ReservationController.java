package controller;

import view.*;

import java.time.*;
import java.util.*;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class ReservationController {

    private Stage stage;
    private FileLoader fileLoader;

    public ReservationController(Stage stage) {
        this.stage = stage;
        this.fileLoader = new FileLoader();
    }

    public TableView<Reservation> makeTable() {
        ArrayList<Reservation> reservations = fileLoader.getReservations();
        ObservableList<Reservation> list = FXCollections.observableArrayList(reservations);

        TableView<Reservation> table = new TableView<>();
        TableColumn<Reservation, String> memberCol = new TableColumn<Reservation, String>("Member");
        TableColumn<Reservation, String> facilityCol = new TableColumn<Reservation, String>("Facility");
        TableColumn<Reservation, String> dateCol = new TableColumn<Reservation, String>("Date");
        TableColumn<Reservation, String> startCol = new TableColumn<Reservation, String>("Start Time");
        TableColumn<Reservation, String> endCol = new TableColumn<Reservation, String>("End Time");
        TableColumn<Reservation, String> statusCol = new TableColumn<Reservation, String>("Status");

        memberCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getMember().getName()));
        facilityCol
                .setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFacility().getFacility()));
        dateCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getDate().toString()));
        startCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getStart().toString()));
        endCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEnd().toString()));
        statusCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getStatus().toString()));

        table.getColumns().setAll(memberCol, facilityCol, dateCol, startCol, endCol, statusCol);
        table.setItems(list);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    public ComboBox<String> getMemberBox() {
        ArrayList<Member> members = fileLoader.getmembers();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Member member : members) {
            list.add(member.getId());
        }
        ComboBox<String> memberBox = new ComboBox<String>();
        memberBox.getItems().addAll(list);
        return memberBox;
    }

    public ComboBox<String> getFacilityBox() {
        ArrayList<Facility> facilities = fileLoader.getFacilities();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Facility facility : facilities) {
            list.add(facility.getFacility());
        }
        ComboBox<String> facilityBox = new ComboBox<String>();
        facilityBox.getItems().addAll(list);
        return facilityBox;
    }

    public void updateTimeBox(MouseEvent event, ComboBox<String> facilityCombo, ComboBox<String> sHCombo) {
        String facilityName = facilityCombo.getValue();
        Facility facility = fileLoader.findFacility(facilityName);
        LocalTime start = facility.getStartHour();
        LocalTime end = facility.getCloseHour();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (LocalTime time = start; !time.equals(end.minus(facility.getDuration())); time = time.plusMinutes(30)) {
            list.add(time.toString());
        }
        sHCombo.getItems().addAll(list);
    }

    public void goToMakeReservation(MouseEvent event) {
        stage.setScene(Main.getScenes().get("MAKE_RESERVATION"));
    }

    public void checkin(MouseEvent event, TableView<Reservation> table) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Reservation reservation = table.getSelectionModel().getSelectedItem();
            if (reservation.getStatus().equals("Booked")) {
                reservation.setStatus("Checked In");
            }
            System.out.println(reservation.getStatus());
        }
        table.refresh();
        fileLoader.writeRecords();
    }

    public void checkout(MouseEvent event, TableView<Reservation> table) {
        if (table.getSelectionModel().getSelectedItem() != null) {
            Reservation reservation = table.getSelectionModel().getSelectedItem();
            if (reservation.getStatus().equals("Checked In")) {
                reservation.setStatus("Checked Out");
            }
            System.out.println(reservation.getStatus());
        }
        table.refresh();
        fileLoader.writeRecords();
    }

    public void back(MouseEvent event) {
        if (SigninController.getStaff() instanceof Manager) {
            stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
        } else if (SigninController.getStaff() instanceof Staff) {
            stage.setScene(Main.getScenes().get("STAFF_NAVIGATION"));
        }
    }

    // TODO: Finish make reservation
    public void makeReservation(MouseEvent event, ComboBox<String> memberComboBox, ComboBox<String> facilityComboBox,
            DatePicker datePicker, ComboBox<String> startComboBox) {
        if (memberComboBox.getValue() == null || facilityComboBox.getValue() == null || datePicker.getValue() == null
                || startComboBox.getValue() == null) {
            System.out.println("Cannot be null");
            return;
        }

        // String memberID = memberComboBox.getValue();
        // String facility = facilityComboBox.getValue();
        // LocalDate date = datePicker.getValue();
        // LocalTime start = LocalTime.parse(startComboBox.getValue());
        // LocalTime end = start.plus(fileLoader.findFacility(facility).getDuration());
        // if (reservationRecord.isAvailable(memberID, facility, date, start)) {
        //     Reservation reservation = new Reservation(fileLoader.findMember(memberID),
        //             fileLoader.findFacility(facility), date, start, end, "Booked");
        //     reservationRecord.addRecord(reservation);
        //     reservationRecord.writeRecord();
        // }

        Main.getScenes().put("RESERVATION", new ReservationView(stage).getScene());
        Main.getScenes().put("MAKE_RESERVATION", new MakeReservationView(stage).getScene());
        stage.setScene(Main.getScenes().get("RESERVATION"));
    }

    public void cancel(MouseEvent event) {
        Main.getScenes().put("MAKE_RESERVATION", new MakeReservationView(stage).getScene());
        stage.setScene(Main.getScenes().get("RESERVATION"));
    }
}