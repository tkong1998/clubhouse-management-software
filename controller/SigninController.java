package controller;

import view.*;

import java.util.*;
import java.io.*;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.stage.Stage;

public class SigninController {

	private Stage stage;
	private ArrayList<Staff> staffList = new ArrayList<Staff>();
	private ArrayList<Manager> managerList = new ArrayList<Manager>();
	private final String path = "data/staff.csv";
	private static Staff staff;

	/** Inject the stage from {@link Main} */
	public SigninController(Stage stage) {
		this.stage = stage;
	}

	public static Staff getStaff() {
		System.out.println(staff.getClass());
		return SigninController.staff;

	}

	private void loadStaff(){
		File file = new File(path);
			if (!file.exists()){
				System.out.println("File does NOT exist!");
			}
		String line, lineData[];
		try (Scanner input = new Scanner(file)) {
			for(int i = 0; input.hasNextLine(); i++){
				line = input.nextLine();
				if (i == 0) {
					if (line.trim().isEmpty()) {
						System.out.println("File is EMPTY!");
					}
					continue;
				} else {
					if (line.trim().isEmpty()){
						continue;
					}
					lineData = line.split(",");
					String staffID = lineData[0].trim();
					String name = lineData[1].trim();
					String password = lineData[2].trim();
					String role = lineData[3].trim();
					
					if (role.equals("staff")){
						staffList.add(new Staff(name, staffID, password));
					} else if (role.equals("manager")) {
						managerList.add(new Manager(name, staffID, password));
					}
				}
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private Staff searchStaff(String name) {
		for (Staff staff : this.staffList) {
			if (staff.getName().equals(name) || staff.getId().equals(name)) {
				return staff;
			}
		}
		for (Manager manager : this.managerList){
			if (manager.getName().equals(name) || manager.getId().equals(name)){
				return manager;
			}
		}
		return null;
	}

	public void signin(MouseEvent event, TextField userTextField, PasswordField pwdField, Label message) {
		loadStaff();
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
