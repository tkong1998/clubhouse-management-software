package view;

import controller.NavigationController;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class StaffNavigationView extends MainView implements ViewMaker{

    private Stage stage;

    public StaffNavigationView(Stage stage) {
        super();
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

        VBox btnBox = new VBox(10);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(10, 25, 10, 25));
        btnBox.getChildren().addAll(sceneTitle,manageReservationBtn,viewUsageBtn,signoutBtn,closeBtn);
        
        BorderPane root = super.getRoot();
        root.setCenter(btnBox);
        
        Scene scene = new Scene(root);
        return scene;
    }

    public Stage getStage(){
        return this.stage;
    }
}