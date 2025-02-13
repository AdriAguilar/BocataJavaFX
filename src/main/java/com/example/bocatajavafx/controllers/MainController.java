package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.MainApp;
import com.example.bocatajavafx.models.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    @FXML
    private Label usernameLabel;

    @FXML
    private Label test;

    @FXML
    private VBox leftPanel;

    @FXML
    private Button logoutBtn;

    @FXML
    public void initialize() {
        String username = LoginController.getUsername();
        String role = LoginController.getRole();

        usernameLabel.setText("Bienvenido, " + username);
        viewByRol(role);
    }

    private void viewByRol(String role) {
        switch (role) {
            case "alumno":
                alumnoView();
                break;

            case "cocina":
                cocinaView();
                break;

            case "administrador":
                adminView();
                break;
        }
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

    private void alumnoView() {
        try {
            FXMLLoader leftLoader = new FXMLLoader(MainApp.class.getResource("fxml/alumno-left-panel.fxml"));
            VBox alumnoLeftPanel = leftLoader.load();
            leftPanel.getChildren().setAll(alumnoLeftPanel);

            test.setText("Soy un alumno");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cocinaView() {
        try {
            FXMLLoader leftLoader = new FXMLLoader(MainApp.class.getResource("fxml/cocina-left-panel.fxml"));
            VBox cocinaLeftPanel = leftLoader.load();
            leftPanel.getChildren().setAll(cocinaLeftPanel);

            test.setText("Soy cocina");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void adminView() {
        try {
            FXMLLoader leftLoader = new FXMLLoader(MainApp.class.getResource("fxml/admin-left-panel.fxml"));
            VBox adminLeftPanel = leftLoader.load();
            leftPanel.getChildren().setAll(adminLeftPanel);

            test.setText("Soy administrador");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
