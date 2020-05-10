package view;

import controller.SigninController;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class SigninView implements ViewMaker {
	
	private Stage stage;
	
	/** Must inject a stage */
	public SigninView(Stage stage) {
		this.stage = stage;
	}

	@Override
	public Scene getScene() {
		
		// Inject stage from Main into controller
		SigninController controller = new SigninController(stage);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text sceneTitle = new Text("Welcome!");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 20));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Text sceneMessage = new Text("Please sign in to perform furthur function...");
        sceneMessage.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        grid.add(sceneMessage, 0, 1, 2, 1);

        Label username = new Label("Username:");
        grid.add(username, 0, 2);

        TextField userTextField = new TextField();
        grid.add(userTextField, 1, 2);

        Label pwd = new Label("Password:");
        grid.add(pwd, 0, 3);

        PasswordField pwdField = new PasswordField();
        grid.add(pwdField, 1, 3);

        Label message = new Label();
        message.setTextFill(Color.RED);
        grid.add(message,0,4,2,1);

        Button signInBtn = new Button("Sign In");
        signInBtn.setOnMousePressed(e -> controller.signin(e,userTextField,pwdField,message));
        
        Button closeBtn = new Button("Close");
        closeBtn.setOnMousePressed(e -> stage.close());
        ButtonBar btnBar = new ButtonBar();
        btnBar.setPadding(new Insets(10));
        btnBar.getButtons().addAll(signInBtn,closeBtn);
        
        BorderPane root = new BorderPane();
        root.setCenter(grid);
        root.setBottom(btnBar);
        Scene scene = new Scene(root,320,240);
		
		return scene;
	}

}