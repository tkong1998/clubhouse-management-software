package controller;

import view.ManagerNavigotionView;
import view.NavigationView;
import view.ReservationView;
import view.SigninView;
import view.ViewUsageView;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	private static Map<String, Scene> scenes = new HashMap<>();
	
	@Override
	public void start(Stage stage) {
		
		scenes.put("SIGNIN", new SigninView(stage).getScene());
		scenes.put("NAVIGATION", new NavigationView(stage).getScene());
		scenes.put("MANAGER_NAVIGATION", new ManagerNavigotionView(stage).getScene());
        scenes.put("RESERVATION", new ReservationView(stage).getScene());
        scenes.put("USAGE", new ViewUsageView(stage).getScene());
		
		stage.setScene(scenes.get("SIGNIN"));
		stage.setTitle("HandShake Club House");
		stage.show();
	}

	public static Map<String, Scene> getScenes() {
		return scenes;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
