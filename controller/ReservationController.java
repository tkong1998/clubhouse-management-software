package controller;

import view.*;

import java.time.LocalDate;
import java.util.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class ReservationController {

    private Stage stage;
    private ReservationRecord reservationRecord;

    public ReservationController(Stage stage) {
        this.stage = stage;
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
        TableColumn<Reservation, Long> durationCol = new TableColumn<Reservation,Long>("Duration");
        TableColumn<Reservation, String> statusCol = new TableColumn<Reservation, String>("Status");

        memberCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getMember().getMemberID()));
        facilityCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getFacility().getFacility()));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        startCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getStart().toString()));
        endCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getEnd().toString()));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        table.getColumns().setAll(memberCol, facilityCol, dateCol, startCol, endCol, durationCol, statusCol);
        table.setItems(list);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

    public void goToMakeReservation(MouseEvent event) {
        stage.setScene(Main.getScenes().get("MAKE_RESERVATION"));
    }

    public void checkin(MouseEvent event, TableView<Reservation> table) {
        if (table.getSelectionModel().getSelectedItem() != null){
            Reservation reservation = table.getSelectionModel().getSelectedItem();
            if (reservation.getStatus().equals("Booked")){
                reservation.setStatus("Checked In");
            }
            System.out.println(reservation.getStatus());
        }
        table.refresh();
        reservationRecord.writeRecord();
    }

    public void checkout(MouseEvent event, TableView<Reservation> table) {
        if (table.getSelectionModel().getSelectedItem() != null){
            Reservation reservation = table.getSelectionModel().getSelectedItem();
            if (reservation.getStatus().equals("Checked In")){
                reservation.setStatus("Checked Out");
            }
            System.out.println(reservation.getStatus());
        }
        table.refresh();
        reservationRecord.writeRecord();
    }

    public void back(MouseEvent event) {
        if (SigninController.getStaff().getRole().equals("staff")) {
            stage.setScene(Main.getScenes().get("STAFF_NAVIGATION"));
        } else if (SigninController.getStaff().getRole().equals("manager")) {
            stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
        }
    }

    // TODO: Finish make reservation
    public void makeReservation(MouseEvent event){
        Main.getScenes().put("MAKE_RESERVATION", new MakeReservationView(stage).getScene());
        stage.setScene(Main.getScenes().get("RESERVATION"));
    }

    public void cancel(MouseEvent event){
        Main.getScenes().put("MAKE_RESERVATION", new MakeReservationView(stage).getScene());
        stage.setScene(Main.getScenes().get("RESERVATION"));
    }
}