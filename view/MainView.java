package view;

import javafx.geometry.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;

public class MainView {
    private BorderPane root;

    public MainView() {
        Text disclaimer = new Text("Individual project by KONG, Tung (20379081)");
        this.root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setBottom(disclaimer);
    }

    public BorderPane getRoot() {
        return this.root;
    }
}