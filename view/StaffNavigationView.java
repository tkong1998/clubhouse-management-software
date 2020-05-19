package view;

import controller.NavigationController;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class StaffNavigationView implements ViewMaker{

    private Stage stage;

    public StaffNavigationView(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene() {
        NavigationController controller = new NavigationController(stage);
        
        Text sceneTitle = new Text("Navigation");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        
        Button manageReservationBtn = new Button("Manage Reservation");
        manageReservationBtn.setOnMousePressed(e -> controller.goToReservation(e));
        
        Button viewUsageBtn = new Button("View Facility Usage");
        viewUsageBtn.setOnMousePressed(e -> controller.goToFacilityUsage(e));

        Button signoutBtn = new Button("Sign Out");
        signoutBtn.setOnMousePressed(e -> controller.signout(e));

        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());

        double btnWidth = 150;
        manageReservationBtn.setMinWidth(btnWidth);
        viewUsageBtn.setMinWidth(btnWidth);
        signoutBtn.setMinWidth(btnWidth);
        closeBtn.setMinWidth(btnWidth);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(25, 25, 25, 25));
        root.getChildren().addAll(sceneTitle,manageReservationBtn,viewUsageBtn,signoutBtn,closeBtn);
        Scene scene = new Scene(root,320,240);
        return scene;
    }

    public Stage getStage(){
        return this.stage;
    }
}