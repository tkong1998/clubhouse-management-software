package view;

import controller.ViewUsageController;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
        
        // TODO: Add Favility Usage information
        Text label = new Text("Remember to deploy a table");

        Button backBtn = new Button("Back");
        backBtn.setOnMousePressed(e -> controller.back(e));
        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());

        HBox btnBar = new HBox(10);
        btnBar.getChildren().addAll(backBtn,closeBtn);

        VBox container = new VBox(10);
        container.setPadding(new Insets(10, 25, 10, 25));
        container.getChildren().addAll(sceneTitle,label,btnBar);

        BorderPane root = super.getRoot();
        root.setCenter(container);

        Scene scene = new Scene(root);

        return scene;
    }
}