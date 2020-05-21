package view;

import java.time.LocalDate;

import controller.*;

import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.*;
import javafx.util.Callback;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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

        ComboBox memberComboBox = new ComboBox<>();
        grid.add(memberComboBox, 1, 1);

        Label facilityLabel = new Label("Facility:");
        grid.add(facilityLabel, 0, 2);

        ComboBox facilityComboBox = new ComboBox<>();
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

        ComboBox startHComboBox = new ComboBox<>();
        Label colonLabel = new Label(":");
        ComboBox startMComboBox = new ComboBox<>();
        HBox startBox = new HBox(5);
        startBox.getChildren().addAll(startHComboBox, colonLabel, startMComboBox);
        grid.add(startBox, 1, 4);

        Label endLabel = new Label("End:");
        grid.add(endLabel, 0, 5);

        ComboBox endHComboBox = new ComboBox<>();
        Label colonLabel2 = new Label(":");
        ComboBox endMComboBox = new ComboBox<>();
        HBox endtBox = new HBox(5);
        endtBox.getChildren().addAll(endHComboBox, colonLabel2, endMComboBox);
        grid.add(endtBox, 1, 5);

        Button submitBtn = new Button("Sumbit");
        submitBtn.setOnMousePressed(e -> controller.makeReservation(e));

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
        btnBox.getChildren().addAll(submitBtn,cancelBtn,closeBtn);
        grid.add(btnBox,0,6,2,1);

        BorderPane root = super.getRoot();
        root.setCenter(grid);
        Scene scene = new Scene(root);
        return scene;
    }

}