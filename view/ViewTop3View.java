package view;

import java.util.Map;

import controller.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ViewTop3View extends MainView {

    private Stage stage;

    public ViewTop3View(Stage stage) {
        super();
        this.stage = stage;
    }
    
    public Scene getScene() {
        ViewTop3Controller controller = new ViewTop3Controller(stage);

        Text sceneTitle = new Text("Three Most Popular Facility");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));

        TableView<Map.Entry<Facility, Map<String, Integer>>> facilities = controller.getFacility();

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
        container.getChildren().addAll(sceneTitle, facilities, btnBar);


        BorderPane root = super.getRoot();
        root.setCenter(container);

        Scene scene = new Scene(root);
        return scene;
    }
}