package controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewUsageController {
    private Stage stage;

    public ViewUsageController(Stage stage) {
		this.stage = stage;
    }

    public void back(MouseEvent event){
        stage.setScene(Main.getScenes().get("NAVIGATION"));
    }
}