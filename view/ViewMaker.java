package view;
import javafx.scene.Scene;

public interface ViewMaker {
	
	/**
	 * Build and return a scene for this view
	 * 
	 * @return the Scene for this view
	 */
	Scene getScene();
}

