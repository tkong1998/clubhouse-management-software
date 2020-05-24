package view;

import controller.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ViewReservationsView extends MainView implements ViewMaker {

    private Stage stage;

    public ViewReservationsView(Stage stage) {
        super();
        this.stage = stage;
    }

    @Override
    public Scene getScene() {
        ReservationController controller = new ReservationController(stage);

        Text sceneTitle = new Text("View All Reservations");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        boolean viewReservationsList = true;
        TableView<Reservation> reservations = controller.getTable(viewReservationsList);

        Button backBtn = new Button("Back");
        backBtn.setOnMousePressed(e -> controller.back(e));
        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());

        double btnWidth = 130;
        backBtn.setMinWidth(btnWidth);
        closeBtn.setMinWidth(btnWidth);

        HBox btnBar = new HBox(10);
        btnBar.setAlignment(Pos.CENTER);
        btnBar.getChildren().addAll(backBtn, closeBtn);

        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 25, 10, 25));
        container.getChildren().addAll(sceneTitle, reservations, btnBar);

        BorderPane root = super.getRoot();
        root.setCenter(container);

        Scene scene = new Scene(root);
        return scene;
    }
}