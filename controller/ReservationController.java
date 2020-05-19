package controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ReservationController {
    
    private Stage stage;

    public ReservationController(Stage stage) {
		this.stage = stage;
    }
    
    // TODO: makeReservation
    public void makeReservation(MouseEvent event){
        // pop out a window to make reservation
        System.out.println("make reservation pressed");
        stage.setScene(Main.getScenes().get(""));
    }
    // TODO: checkin
    public void checkin(MouseEvent event){
        System.out.println("checkin pressed");
    }
    // TODO: checkout
    public void checkout(MouseEvent event){
        System.out.println("checkout pressed");

    }

    public void back(MouseEvent event){
        if (SigninController.getStaff().getRole().equals("staff")){
            stage.setScene(Main.getScenes().get("NAVIGATION"));
        } else if (SigninController.getStaff().getRole().equals("manager")){
            stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
        }
    }

    // TODO: display
    public void display(){

    }
}