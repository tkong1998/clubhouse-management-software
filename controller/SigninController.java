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
	private String path = "data/staff.csv";
	private static Staff staff;
	
	/** Inject the stage from {@link Main} */
	public SigninController(Stage stage) {
		this.stage = stage;
	}

	public static Staff getStaff(){
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
					String email = lineData[2].trim();
					String password = lineData[3].trim();
					String role = lineData[4].trim();
			
					Staff staff = new Staff(staffID,name,password,email,role);
					staffList.add(staff);
				}
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private Staff searchStaff(String name){
		for (Staff staff: this.staffList){
			if (staff.getName().equals(name) || staff.getStaffID().equals(name)) {
				return staff;
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
		String staffPwd = staff.getPassword();
		String staffRole = staff.getRole();

		if (!staffPwd.equals(pwd)){
			message.setText("Password does NOT match!");
			return;
		}
		if (staffRole.equals("staff")) {
			message.setText("");
			stage.setScene(Main.getScenes().get("STAFF_NAVIGATION"));
		} else if (staffRole.equals("manager")) {
			message.setText("");
			stage.setScene(Main.getScenes().get("MANAGER_NAVIGATION"));
		} else {
			message.setText("You have no authority to login to this system");
		}
		Main.getScenes().put("SIGNIN", new SigninView(stage).getScene());
	}

	public static void signout(){
		SigninController.staff = null;
	}

}
