package view;

import java.time.*;

import controller.*;
import javafx.beans.property.*;
import javafx.collections.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ViewUsageView extends MainView {

    private Stage stage;

    public ViewUsageView(Stage stage) {
        super();
        this.stage = stage;

    }

    public Scene getScene() {
        ViewUsageController controller = new ViewUsageController(stage);

        Text sceneTitle = new Text("View Facility Usage");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());

        ComboBox<String> timeComboBox = new ComboBox<>();
        for (LocalTime time = LocalTime.parse("08:00"); !time.isAfter(LocalTime.parse("22:00")); time = time
                .plusMinutes(30)) {
            timeComboBox.getItems().add(time.toString());
        }
        timeComboBox.getSelectionModel().selectFirst();

        Label message = new Label();
        message.setTextFill(Color.RED);
        message.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));

        HBox timeBox = new HBox(10);
        timeBox.getChildren().addAll(datePicker, timeComboBox, message);

        TableView<String[]> table = new TableView<>();
        TableColumn<String[], String> facilityCol = new TableColumn<String[], String>("Facility");
        TableColumn<String[], String> desctiptCol = new TableColumn<String[], String>("Description");
        TableColumn<String[], String> bookingsCol = new TableColumn<String[], String>("Bookings");
        TableColumn<String[], String> availCol = new TableColumn<String[], String>("Available");
        TableColumn<String[], String> capCol = new TableColumn<String[], String>("Capacity");
        TableColumn<String[], String> statusCol = new TableColumn<String[], String>("Status");

        facilityCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()[0]));
        desctiptCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()[1]));
        bookingsCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()[2]));
        availCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()[3]));
        capCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()[4]));
        statusCol.setCellValueFactory(value -> new SimpleStringProperty(value.getValue()[5]));

        table.getColumns().setAll(facilityCol, bookingsCol, availCol, capCol, statusCol, desctiptCol);
        ObservableList<String[]> list = controller.getUsageList(datePicker, timeComboBox, message);
        table.setItems(list);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Button refreshBtn = new Button("Refresh");
        refreshBtn.setOnMousePressed(e -> controller.updateTable(table, datePicker, timeComboBox, message));
        Button backBtn = new Button("Back");
        backBtn.setOnMousePressed(e -> controller.back(e));
        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());

        double btnWidth = 130;
        refreshBtn.setMinWidth(btnWidth);
        backBtn.setMinWidth(btnWidth);
        closeBtn.setMinWidth(btnWidth);

        HBox btnBar = new HBox(10);
        btnBar.getChildren().addAll(refreshBtn, backBtn, closeBtn);

        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 25, 10, 25));
        container.getChildren().addAll(sceneTitle, timeBox, table, btnBar);

        BorderPane root = super.getRoot();
        root.setCenter(container);

        Scene scene = new Scene(root);

        return scene;
    }
}