package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.services.AlumnoService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;

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

        if (verifyPassword(password, "$2y$10$vdvUSW1raVeE0qHte1sgiO3klJs92VP7.QEf3jcSDJPfnMWzvYz6K")) {
            System.out.println("Funciona!");
        } else {
            System.out.println("No coinciden.");
        }

//        System.out.println("Usuario: " + username);
//        System.out.println("Contraseña: " + password);
//
//        AlumnoService alumnoService = new AlumnoService();
//        List<Alumno> alumnos = alumnoService.getAll();
//
//        for (Alumno alumno : alumnos) {
//            System.out.println(alumno);
//        }
    }

    public boolean verifyPassword(String pw, String hash) {
        if (hash.startsWith("$2y$")) {
            hash = "$2a$" + hash.substring(4);
        }
        return BCrypt.checkpw(pw, hash);
    }
}
