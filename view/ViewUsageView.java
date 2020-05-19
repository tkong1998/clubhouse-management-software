package view;

import controller.ViewUsageController;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ViewUsageView implements ViewMaker{

    private Stage stage;

    public ViewUsageView(Stage stage) {
        this.stage = stage;
    }

    @Override
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

        VBox root = new VBox(10);
        root.setPadding(new Insets(25, 25, 25, 25));
        root.getChildren().addAll(sceneTitle,label,btnBar);
        Scene scene = new Scene(root);
        return scene;
    }
}