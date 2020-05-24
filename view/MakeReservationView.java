package view;

import java.time.LocalDate;

import controller.*;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class MakeReservationView extends MainView implements ViewMaker {
    private Stage stage;

    public MakeReservationView(Stage stage) {
        super();
        this.stage = stage;
    }

    @Override
    public Scene getScene() {
        ReservationController controller = new ReservationController(stage);
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 25, 10, 25));
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Text sceneTitle = new Text("Make Reservation");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label memberLabel = new Label("Member:");
        grid.add(memberLabel, 0, 1);

        ComboBox<String> memberComboBox = controller.getMemberBox();
        grid.add(memberComboBox, 1, 1);

        Label facilityLabel = new Label("Facility:");
        grid.add(facilityLabel, 0, 2);

        ComboBox<String> facilityComboBox = controller.getFacilityBox();
        grid.add(facilityComboBox, 1, 2);

        Label dateLabel = new Label("Date:");
        grid.add(dateLabel, 0, 3);

        DatePicker datePicker = new DatePicker();
        // disable dates that are past
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (date.isBefore(LocalDate.now().plusDays(1))) {
                            setDisable(true);
                            setStyle("-fx-background-color: #bfbfbf;");
                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
        grid.add(datePicker, 1, 3);

        Label startLabel = new Label("Start:");
        grid.add(startLabel, 0, 4);

        ComboBox<String> startComboBox = new ComboBox<String>();
        grid.add(startComboBox, 1, 4);
        startComboBox.setOnMousePressed(e -> controller.updateTimeBox(e, facilityComboBox, startComboBox));

        Label message = new Label();
        message.setTextFill(Color.RED);
        message.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid.add(message, 0, 5, 2, 1);

        Button submitBtn = new Button("Sumbit");
        submitBtn.setOnMousePressed(
                e -> controller.makeReservation(e, memberComboBox, facilityComboBox, datePicker, startComboBox, message));

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setOnMousePressed(e -> controller.cancel(e));

        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());

        double btnWidth = 80;
        submitBtn.setMinWidth(btnWidth);
        cancelBtn.setMinWidth(btnWidth);
        closeBtn.setMinWidth(btnWidth);

        HBox btnBox = new HBox(5);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().addAll(submitBtn, cancelBtn, closeBtn);
        grid.add(btnBox, 0, 6, 2, 1);

        BorderPane root = super.getRoot();
        root.setCenter(grid);
        Scene scene = new Scene(root);
        return scene;
    }

}