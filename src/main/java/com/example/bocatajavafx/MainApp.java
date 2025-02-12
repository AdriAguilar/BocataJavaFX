package com.example.bocatajavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 280);
        stage.setTitle("Iniciar Sesión - BocataFX");
        stage.setScene(scene);

        stage.setMinWidth(300);
        stage.setMinHeight(275);

        stage.setResizable(true);

        stage.show();
    }
}
