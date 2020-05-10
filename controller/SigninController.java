package controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SigninController {

	private Stage stage;
	
	/** Inject the stage from {@link Main} */
	public SigninController(Stage stage) {
		this.stage = stage;
	}

	/** Display the first scene */
	public void signin(MouseEvent event) {
		stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
	}
}
