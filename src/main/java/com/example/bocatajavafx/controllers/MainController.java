package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Button logoutBtn;

    public void initialize() {
        String username = LoginController.getUsername();
        usernameLabel.setText("Bienvenido, " + username);
    }

    public void handleLogout() throws IOException {
        Stage currentStage = (Stage) logoutBtn.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 280);

        Stage stage = new Stage();
        stage.setTitle("Iniciar Sesión - BocataFX");
        stage.setScene(scene);

        stage.setMinWidth(300);
        stage.setMinHeight(275);

        stage.setResizable(true);

        stage.show();
    }
}
