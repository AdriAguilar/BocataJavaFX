package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.MainApp;
import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.models.Usuario;
import com.example.bocatajavafx.services.AlumnoService;
import com.example.bocatajavafx.services.LoginResponse;
import com.example.bocatajavafx.services.UsuarioService;
import com.example.bocatajavafx.util.ValidatorUtil;
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
    private static String role;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginBtn;

    @FXML
    private void handleLogin() throws IOException {
        String user = emailField.getText();
        String password = passwordField.getText();

        LoginResponse response = login(user, password, ValidatorUtil.isAlumno(user));

        if (response.isSuccess()) {
            username = response.getMessage();
            role = response.getRole();
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
    public static String getRole() {
        return role;
    }

    private LoginResponse login(String user, String password, boolean isAlumno) {
        if (isAlumno) {
            AlumnoService alumnoService = new AlumnoService();
            return alumnoService.loginAlumno(user, password);
        } else {
            UsuarioService usuarioService = new UsuarioService();
            return usuarioService.loginUsuario(user, password);
        }
    }
}
