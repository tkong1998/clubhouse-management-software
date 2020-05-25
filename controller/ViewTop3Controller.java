package controller;

import java.util.*;
import java.util.Map.*;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewTop3Controller {

    private Stage stage;
    private FileLoader fileLoader;

    public ViewTop3Controller(Stage stage) {
        this.stage = stage;
        this.fileLoader = new FileLoader();
    }

    public TableView<Map.Entry<Facility, Map<String, Integer>>> getFacility() {
        PriorityQueue<Map.Entry<Facility, Map<String, Integer>>> facilities = fileLoader.getThreeMostPopularFacility();
        ObservableList<Entry<Facility, Map<String, Integer>>> list = FXCollections.observableArrayList(facilities);

        TableView<Map.Entry<Facility, Map<String, Integer>>> table = new TableView<>();
        TableColumn<Map.Entry<Facility, Map<String, Integer>>, String> facilityCol = new TableColumn<Map.Entry<Facility, Map<String, Integer>>, String>(
                "Facility");
        TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer> bookCol = new TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer>(
                "Booked");
        TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer> cancelCol = new TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer>(
                "Cancelled");
        TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer> checkinCol = new TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer>(
                "Checked In");
        TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer> checkoutCol = new TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer>(
                "Checked Out");
        TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer> lateCol = new TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer>(
                "Late");
        TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer> noShowCol = new TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer>(
                "No Show");
        TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer> totalCol = new TableColumn<Map.Entry<Facility, Map<String, Integer>>, Integer>(
                "Total");

        facilityCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue().getKey().getFacilityName()));
        bookCol.setCellValueFactory(
                value -> new SimpleIntegerProperty(value.getValue().getValue().get("Booked")).asObject());
        cancelCol.setCellValueFactory(
                value -> new SimpleIntegerProperty(value.getValue().getValue().get("Cancelled")).asObject());
        checkinCol.setCellValueFactory(
                value -> new SimpleIntegerProperty(value.getValue().getValue().get("Checked In")).asObject());
        checkoutCol.setCellValueFactory(
                value -> new SimpleIntegerProperty(value.getValue().getValue().get("Checked Out")).asObject());
        lateCol.setCellValueFactory(
            value -> new SimpleIntegerProperty(value.getValue().getValue().get("Late")).asObject());
        noShowCol.setCellValueFactory(
                value -> new SimpleIntegerProperty(value.getValue().getValue().get("No Show")).asObject());
        totalCol.setCellValueFactory(
                value -> new SimpleIntegerProperty(value.getValue().getValue().get("Total")).asObject());
        

        table.getColumns().setAll(facilityCol, bookCol, cancelCol, checkinCol, checkoutCol, lateCol, noShowCol,
                totalCol);
        table.setItems(list);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        return table;
    }

	public void back(MouseEvent e) {
		stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
	}

}