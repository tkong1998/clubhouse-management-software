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
        ObservableList<Reservation> list = FXCollections.observableArrayList();
        for (Reservation reservation : reservations) {
            if (reservation.getDate().isBefore(LocalDate.now()) || (reservation.getStatus().equals("Checked Out"))) {
                continue;
            }
            list.add(reservation);
        }

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
        memberBox.setPromptText("Select a member");
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
        facilityBox.setPromptText("Select a facility");
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
                table.getItems().remove(reservation);
            }
            System.out.println(reservation.getStatus());
        }
        table.refresh();
        fileLoader.writeRecords();
    }

    public void cancelBooking(MouseEvent e, TableView<Reservation> table) {
		if (table.getSelectionModel().getSelectedItem() != null) {
            Reservation reservation = table.getSelectionModel().getSelectedItem();
            if (reservation.getStatus().equals("Booked")) {
                reservation.setStatus("Cancelled");
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

    public void makeReservation(MouseEvent event, ComboBox<String> memberComboBox, ComboBox<String> facilityComboBox,
            DatePicker datePicker, ComboBox<String> startComboBox, Label message) {
        if (memberComboBox.getValue() == null || facilityComboBox.getValue() == null || datePicker.getValue() == null
                || startComboBox.getValue() == null) {
            message.setText("Invalid reservation, please check again.");

            return;
        }

        Member member = fileLoader.findMember(memberComboBox.getValue());
        Facility facility = fileLoader.findFacility(facilityComboBox.getValue());
        LocalDate date = datePicker.getValue();
        LocalTime start = LocalTime.parse(startComboBox.getValue());
        LocalTime end = start.plus(facility.getDuration());

        if (fileLoader.isValid(member, facility, date, start)) {
            Reservation reservation = new Reservation(member, facility, date, start, end, "Booked");
            fileLoader.getReservations().add(reservation);
            fileLoader.writeRecords();
            Main.getScenes().put("RESERVATION", new ReservationView(stage).getScene());
            Main.getScenes().put("MAKE_RESERVATION", new MakeReservationView(stage).getScene());
            stage.setScene(Main.getScenes().get("RESERVATION"));
        } else {
            message.setText("Invalid reservation, please check again.");
            return;
        }
    }

    public void cancel(MouseEvent event) {
        Main.getScenes().put("MAKE_RESERVATION", new MakeReservationView(stage).getScene());
        stage.setScene(Main.getScenes().get("RESERVATION"));
    }
}