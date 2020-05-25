package controller;

import view.*;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class SigninController {

	private Stage stage;
	private FileLoader fileLoader;
	private static Staff staff;

	public SigninController(Stage stage) {
		this.stage = stage;
		this.fileLoader = new FileLoader();
	}

	public static Staff getStaff() {
		return SigninController.staff;

	}

	private Staff searchStaff(String name) {
		for (Staff staff : fileLoader.getStaffs()) {
			if (staff.getName().equals(name) || staff.getId().equals(name)) {
				return staff;
			}
		}
		for (Manager manager : fileLoader.getManagers()){
			if (manager.getName().equals(name) || manager.getId().equals(name)){
				return manager;
			}
		}
		return null;
	}

	public void signin(MouseEvent event, TextField userTextField, PasswordField pwdField, Label message) {
		String user = userTextField.getText();
		String pwd = pwdField.getText();
		staff = searchStaff(user);
		if (staff == null) {
			message.setText("User does NOT exist!");
			return;
		}
		String password = staff.getPassword();
		if (!password.equals(pwd)) {
			message.setText("Password does NOT match!");
			return;
		}
		if (staff instanceof Manager) {
			message.setText("");
			stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
		} else if (staff instanceof Staff) {
			message.setText("");
			stage.setScene(Main.getScenes().get("STAFF_NAVIGATION"));
		} else {
			message.setText("You have no authority to login to this system");
		}
		Main.getScenes().put("SIGNIN", new SigninView(stage).getScene());
	}

	public static void signout() {
		SigninController.staff = null;
	}

}
