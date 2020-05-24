package controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class NavigationController {
  private Stage stage;

  public NavigationController(Stage stage) {
    this.stage = stage;
  }

  public void goToReservation(MouseEvent event) {
    stage.setScene(Main.getScenes().get("RESERVATION"));
  }

  public void goToFacilityUsage(MouseEvent event) {
    stage.setScene(Main.getScenes().get("USAGE"));
  }

  public void viewReservation(MouseEvent event) {
    stage.setScene(Main.getScenes().get("VIEW_RESERVATION"));
  }

  public void viewPopularFacility(MouseEvent event) {
    stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
  }

  public void viewUtilizationRate(MouseEvent event) {
    stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
  }

public void signout(MouseEvent e) {
  SigninController.signout();
  stage.setScene(Main.getScenes().get("SIGNIN"));
}
}