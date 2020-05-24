package controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewUsageController {
    private Stage stage;

    public ViewUsageController(Stage stage) {
		this.stage = stage;
    }

    public void back(MouseEvent event){
      if (SigninController.getStaff() instanceof Manager){
        stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
    } else if (SigninController.getStaff() instanceof Staff){
        stage.setScene(Main.getScenes().get("STAFF_NAVIGATION"));
    }
    }
}