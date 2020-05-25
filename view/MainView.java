package view;

import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.scene.control.*;

public class MainView {
    private BorderPane root;

    public MainView() {
        Label disclaimer = new Label("Individual project by KONG, Tung (20379081)");
        disclaimer.setFont(Font.font("Tahoma", FontWeight.NORMAL, 12));
        this.root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setBottom(disclaimer);
    }

    public BorderPane getRoot() {
        return this.root;
    }
}