package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.MainApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;

import java.io.IOException;

public class CocinaController {
    private MainController mainController;

    @FXML
    private Button pedidosBtn;

    @FXML
    private Button menuBtn;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    public void initialize() {
        pedidosBtn.setOnAction(event -> changeView("fxml/pedidos-cocina-view.fxml"));
        menuBtn.setOnAction(event -> changeView("fxml/menu-view.fxml"));
    }

    private void changeView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlPath));
            Node view = loader.load();
            if (mainController != null) {
                mainController.setCenter(view);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
