package controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ViewUsageController {
    private Stage stage;

    public ViewUsageController(Stage stage) {
		this.stage = stage;
    }

    public void back(MouseEvent event){
      if (SigninController.staff.getRole().equals("staff")){
        stage.setScene(Main.getScenes().get("NAVIGATION"));
    } else if (SigninController.staff.getRole().equals("manager")){
        stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
    }
    }
}