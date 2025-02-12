package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.MainApp;
import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.services.AlumnoService;
import com.example.bocatajavafx.services.LoginResponse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    private static String username;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private void handleLogin() throws IOException {
        String email = emailField.getText();
        String password = passwordField.getText();

        AlumnoService alumnoService = new AlumnoService();
        LoginResponse response = alumnoService.loginAlumno(email, password);

        if (response.isSuccess()) {
            username = response.getMessage();
            openMain();
        } else {
            System.out.println(response.getMessage());
        }
    }

    @FXML
    private void openMain() throws IOException {
        Stage currentStage = (Stage) loginBtn.getScene().getWindow();
        currentStage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("fxml/main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1150, 740);

        Stage stage = new Stage();
        stage.setTitle("BocataFX");
        stage.setScene(scene);

        stage.setMinWidth(800);
        stage.setMinHeight(675);

        stage.setResizable(true);

        stage.show();
    }

    public static String getUsername() {
        return username;
    }
}
