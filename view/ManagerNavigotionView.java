package view;

import controller.NavigationController;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ManagerNavigotionView extends MainView {
    private Stage stage; 

    public ManagerNavigotionView(Stage stage) {
        super();
        this.stage = stage;
        }

    public Scene getScene() {
        NavigationController controller = new NavigationController(stage);

        Text sceneTitle = new Text("Navigation");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        
        Button manageReservationBtn = new Button("Manage Reservation");
        manageReservationBtn.setOnMousePressed(e -> controller.goToReservation(e));
        
        Button viewUsageBtn = new Button("View Facility Usage");
        viewUsageBtn.setOnMousePressed(e -> controller.goToFacilityUsage(e));

        Button viewReservationBtn = new Button("View Reservations List");
        viewReservationBtn.setOnMouseClicked(e -> controller.viewReservation(e));
       
        Button viewPopularBtn = new Button("View Top 3 Popular Facility");
        viewPopularBtn.setOnMouseClicked(e -> controller.viewPopularFacility(e));

        Button signoutBtn = new Button("Sign Out");
        signoutBtn.setOnMousePressed(e -> controller.signout(e));

        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());

        double btnWidth = 200;
        manageReservationBtn.setMinWidth(btnWidth);
        viewUsageBtn.setMinWidth(btnWidth);
        viewReservationBtn.setMinWidth(btnWidth);
        viewPopularBtn.setMinWidth(btnWidth);
        signoutBtn.setMinWidth(btnWidth);
        closeBtn.setMinWidth(btnWidth);

        VBox btnBox = new VBox(10);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setPadding(new Insets(10, 25, 10, 25));
        btnBox.getChildren().addAll(sceneTitle,manageReservationBtn,viewUsageBtn,viewReservationBtn,viewPopularBtn,signoutBtn,closeBtn);
        
        BorderPane root = super.getRoot();
        root.setCenter(btnBox);
        
        Scene scene = new Scene(root);

        return scene;
    }
    
}