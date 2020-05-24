package controller;

import java.time.*;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewUsageController {
  private Stage stage;
  private FileLoader fileLoader;

  public ViewUsageController(Stage stage) {
    this.stage = stage;
    this.fileLoader = new FileLoader();
  }

  public ObservableList<String[]> getUsageList(DatePicker datepicker, ComboBox<String> timeComboBox, Label message) {
    LocalDate date = datepicker.getValue();
    LocalTime time = LocalTime.now();
    try {
      time = LocalTime.parse(timeComboBox.getSelectionModel().getSelectedItem());
    } catch (Exception e) {
      e.getStackTrace();
      message.setText("Please enter the time in ##:## format");
    }
    ArrayList<Facility> facilities = fileLoader.getFacilities();
    ArrayList<Reservation> reservations = fileLoader.getReservations();
    ObservableList<String[]> list = FXCollections.observableArrayList();

    for (Facility facility : facilities) {
      int count = 0;
      int capacity = facility.getCapacity();
      int avail = capacity - count;
      String usageStatus = "Available";
      String description = facility.getDescription();
      if (time.isBefore(facility.getStartHour()) || time.isAfter(facility.getCloseHour())) {
        usageStatus = "Close";
        String[] info = { facility.getFacility(), description, String.valueOf(count), String.valueOf(avail),
            String.valueOf(capacity), usageStatus };
        list.add(info);
        continue;
      }
      LocalTime end = time.plus(facility.getDuration());
      for (Reservation reservation : reservations) {
        if (reservation.getFacility().equals(facility)) {
          if (reservation.getDate().equals(date)) {
            if ((!time.isBefore(reservation.getStart()) && !time.isAfter(reservation.getEnd()))
                || (!end.isBefore(reservation.getStart()) && !end.isAfter(reservation.getEnd()))) {
              count += 1;
            }
          }
        }
      }
      avail = capacity - count;
      if (avail == 0) {
        usageStatus = "Full";
      } else {
        usageStatus = "Available";
      }
      String[] info = { facility.getFacility(), description, String.valueOf(count), String.valueOf(avail),
          String.valueOf(capacity), usageStatus };
      list.add(info);
    }

    return list;
  }

  public void back(MouseEvent event) {
    if (SigninController.getStaff() instanceof Manager) {
      stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
    } else if (SigninController.getStaff() instanceof Staff) {
      stage.setScene(Main.getScenes().get("STAFF_NAVIGATION"));
    }
  }

  public void updateTable(TableView<String[]> table, DatePicker datePicker, ComboBox<String> timeComboBox,
      Label message) {
    table.getItems().clear();
    table.getItems().addAll(getUsageList(datePicker, timeComboBox, message));
    table.refresh();
  }
}