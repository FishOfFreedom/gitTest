package com.example.rlguibuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GuiBuilder extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setPrefSize(1020,690);

        Scene scene = new Scene(borderPane, null);
        stage.setScene(scene);
        stage.setTitle("Gui Builder");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
