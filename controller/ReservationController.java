package controller;

import view.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class ReservationController {

    private Stage stage;
    private MemberList memberList;
    private FacilityList facilityList;
    private ReservationRecord reservationRecord;

    public ReservationController(Stage stage) {
        this.stage = stage;
        this.memberList = new MemberList();
        this.facilityList = new FacilityList();
        this.reservationRecord = new ReservationRecord();
    }

    public TableView<Reservation> makeTable() {
        ArrayList<Reservation> reservationList = reservationRecord.getRecords();
        ObservableList<Reservation> list = FXCollections.observableArrayList(reservationList);

        TableView<Reservation> table = new TableView<>();
        TableColumn<Reservation, String> memberCol = new TableColumn<Reservation, String>("Member ID");
        TableColumn<Reservation, String> facilityCol = new TableColumn<Reservation, String>("Facility");
        TableColumn<Reservation, LocalDate> dateCol = new TableColumn<Reservation, LocalDate>("Date");
        TableColumn<Reservation, String> startCol = new TableColumn<Reservation, String>("Start Time");
        TableColumn<Reservation, String> endCol = new TableColumn<Reservation, String>("End Time");
        TableColumn<Reservation, String> statusCol = new TableColumn<Reservation, String>("Status");

        memberCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getMember().getId()));
        facilityCol
                .setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFacility().getFacility()));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getStart().toString()));
        endCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEnd().toString()));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().setAll(memberCol, facilityCol, dateCol, startCol, endCol, statusCol);
        table.setItems(list);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    public ComboBox<String> getMemberBox() {
        ArrayList<Member> members = memberList.getMemberList();
        ObservableList<String> list = FXCollections.observableArrayList();
        for (Member member : members) {
            list.add(member.getId());
        }
        ComboBox<String> memberBox = new ComboBox<String>();
        memberBox.getItems().addAll(list);
        return memberBox;
    }

    public ComboBox<String> getFacilityBox() {
        ArrayList<Facility> facilities = facilityList.getFacilityList();
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
        Facility facility = facilityList.findFacility(facilityName);
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
        reservationRecord.writeRecord();
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
        reservationRecord.writeRecord();
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

        String memberID = memberComboBox.getValue();
        String facility = facilityComboBox.getValue();
        LocalDate date = datePicker.getValue();
        LocalTime start = LocalTime.parse(startComboBox.getValue());
        LocalTime end = start.plus(facilityList.findFacility(facility).getDuration());
        if (reservationRecord.isAvailable(memberID, facility, date, start)) {
            Reservation reservation = new Reservation(memberList.findMember(memberID),
                    facilityList.findFacility(facility), date, start, end, "Booked");
            reservationRecord.addRecord(reservation);
            reservationRecord.writeRecord();
        }

        Main.getScenes().put("RESERVATION", new ReservationView(stage).getScene());
        Main.getScenes().put("MAKE_RESERVATION", new MakeReservationView(stage).getScene());
        stage.setScene(Main.getScenes().get("RESERVATION"));
    }

    public void cancel(MouseEvent event) {
        Main.getScenes().put("MAKE_RESERVATION", new MakeReservationView(stage).getScene());
        stage.setScene(Main.getScenes().get("RESERVATION"));
    }
}