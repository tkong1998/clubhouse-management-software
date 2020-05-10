package view;

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

        ButtonBar btnBar = new ButtonBar();
        btnBar.setPadding(new Insets(10));
        btnBar.getButtons().addAll(makeReservationBtn,checkinBtn,checkoutBtn,backBtn,closeBtn);

        VBox root = new VBox(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        root.getChildren().addAll(sceneTitle,label,btnBar);
        Scene scene = new Scene(root);
        return scene;
    }
}