package view;

import controller.Reservation;
import controller.ReservationController;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ReservationView implements ViewMaker{

    private Stage stage;

    public ReservationView(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene() {
        ReservationController controller = new ReservationController(stage);
        
        Text sceneTitle = new Text("Manage Reservations");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        
        // TODO: Add reservation information
        Text label = new Text("Remember to deploy a table");
        TableView<Reservation> reservations = new TableView<Reservation>();

        Button makeReservationBtn = new Button("Make Reservation");
        makeReservationBtn.setOnMousePressed(e -> controller.makeReservation(e));
        Button checkinBtn = new Button("Check In");
        checkinBtn.setOnMousePressed(e -> controller.checkin(e));
        Button checkoutBtn = new Button("Check Out");
        checkoutBtn.setOnMousePressed(e -> controller.checkout(e));
        Button backBtn = new Button("Back");
        backBtn.setOnMousePressed(e -> controller.back(e));
        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());

        double btnWidth = 130;
        makeReservationBtn.setMinWidth(btnWidth);
        checkinBtn.setMinWidth(btnWidth);
        checkoutBtn.setMinWidth(btnWidth);
        backBtn.setMinWidth(btnWidth);
        closeBtn.setMinWidth(btnWidth);

        HBox btnBar = new HBox(10);
        btnBar.setAlignment(Pos.CENTER);
        btnBar.getChildren().addAll(makeReservationBtn,checkinBtn,checkoutBtn,backBtn,closeBtn);

        VBox root = new VBox(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        root.getChildren().addAll(sceneTitle,label,reservations,btnBar);
        Scene scene = new Scene(root);
        return scene;
    }
}