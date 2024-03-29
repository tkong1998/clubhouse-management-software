package view;

import controller.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ReservationView extends MainView {

    private Stage stage;

    public ReservationView(Stage stage) {
        super();
        this.stage = stage;
    }

    public Scene getScene() {
        ReservationController controller = new ReservationController(stage);

        Text sceneTitle = new Text("Manage Reservations");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        boolean manageReservation = false;
        TableView<Reservation> reservations = controller.getTable(manageReservation);

        Button makeReservationBtn = new Button("Make Reservation");
        makeReservationBtn.setOnMousePressed(e -> controller.goToMakeReservation(e));
        Button checkinBtn = new Button("Check In");
        checkinBtn.setOnMousePressed(e -> controller.checkin(e, reservations));
        Button checkoutBtn = new Button("Check Out");
        checkoutBtn.setOnMousePressed(e -> controller.checkout(e, reservations));
        Button cancelBtn = new Button("Cancel Booking");
        cancelBtn.setOnMousePressed(e -> controller.cancelBooking(e, reservations));
        Button backBtn = new Button("Back");
        backBtn.setOnMousePressed(e -> controller.back(e));
        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());

        double btnWidth = 130;
        makeReservationBtn.setMinWidth(btnWidth);
        checkinBtn.setMinWidth(btnWidth);
        checkoutBtn.setMinWidth(btnWidth);
        cancelBtn.setMinWidth(btnWidth);
        backBtn.setMinWidth(btnWidth);
        closeBtn.setMinWidth(btnWidth);

        HBox btnBar = new HBox(10);
        btnBar.setAlignment(Pos.CENTER);
        btnBar.getChildren().addAll(makeReservationBtn, checkinBtn, checkoutBtn, cancelBtn, backBtn, closeBtn);

        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 25, 10, 25));
        container.getChildren().addAll(sceneTitle, reservations, btnBar);

        BorderPane root = super.getRoot();
        root.setCenter(container);

        Scene scene = new Scene(root);
        return scene;
    }
}