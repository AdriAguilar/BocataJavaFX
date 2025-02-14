package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.MainApp;
import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.models.Usuario;
import com.example.bocatajavafx.services.AlumnoService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {
    private static Alumno alumno;
    @FXML
    private Label usernameLabel;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button logoutBtn;

    @FXML
    public void initialize() {
        String username = LoginController.getUsername();
        String role = LoginController.getRole();

        AlumnoService alumnoService = new AlumnoService();
        alumno = alumnoService.getAlumnoByName(username);

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

        stage.setResizable(false);

        stage.show();
    }

    private void alumnoView() {
        try {
            FXMLLoader leftLoader = new FXMLLoader(MainApp.class.getResource("fxml/alumno-left-panel.fxml"));
            FXMLLoader pedirBocataLoader = new FXMLLoader(MainApp.class.getResource("fxml/pedir-bocata-view.fxml"));

            Node alumnoLeftPanel = leftLoader.load();
            Node pedirBocata = pedirBocataLoader.load();

            AlumnoController leftController = leftLoader.getController();
            leftController.setMainController(this);

            borderPane.setLeft(alumnoLeftPanel);
            borderPane.setCenter(pedirBocata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cocinaView() {
        try {
            FXMLLoader leftLoader = new FXMLLoader(MainApp.class.getResource("fxml/cocina-left-panel.fxml"));
            FXMLLoader pedidosCocinaLoader = new FXMLLoader(MainApp.class.getResource("fxml/pedidos-cocina-view.fxml"));

            Node cocinaLeftPanel = leftLoader.load();
            Node pedidosCocina = pedidosCocinaLoader.load();

            CocinaController leftController = leftLoader.getController();
            leftController.setMainController(this);

            borderPane.setLeft(cocinaLeftPanel);
            borderPane.setCenter(pedidosCocina);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void adminView() {
        try {
            FXMLLoader leftLoader = new FXMLLoader(MainApp.class.getResource("fxml/admin-left-panel.fxml"));
            FXMLLoader pedirBocataLoader = new FXMLLoader(MainApp.class.getResource("fxml/pedir-bocata-view.fxml"));

            Node adminLeftPanel = leftLoader.load();
            Node pedirBocata = pedirBocataLoader.load();

            AdminController leftController = leftLoader.getController();
            leftController.setMainController(this);

            borderPane.setLeft(adminLeftPanel);
            borderPane.setCenter(pedirBocata);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCenter(Node newCenter) {
        if (borderPane != null) {
            borderPane.setCenter(newCenter);
        }
    }

    public static Alumno getAlumno() {
        return alumno;
    }
}
