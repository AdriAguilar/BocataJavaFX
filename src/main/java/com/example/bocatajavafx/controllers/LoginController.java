package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.services.AlumnoService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        System.out.println("Usuario: " + username);
        System.out.println("Contraseña: " + password);

        AlumnoService alumnoService = new AlumnoService();
        List<Alumno> alumnos = alumnoService.getAll();

        for (Alumno alumno : alumnos) {
            System.out.println(alumno);
        }
    }
}
