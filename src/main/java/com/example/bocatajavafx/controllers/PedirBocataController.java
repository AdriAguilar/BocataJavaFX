package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.services.BocadilloResponse;
import com.example.bocatajavafx.services.PedidoService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PedirBocataController {
    @FXML
    private Button BFrio;

    @FXML
    private Button BCaliente;

    @FXML
    private Label isOrdered;

    private PedidoService pedidoService;

    public void initialize() {
        pedidoService = new PedidoService();
        BFrio.setOnAction(event -> handleBocata("frio"));
        BCaliente.setOnAction(event -> handleBocata("caliente"));
    }

    private void handleBocata(String tipo) {
        BocadilloResponse response = pedidoService.createPedido(MainController.getAlumno(), tipo);
        if (response.isSuccess()) {
            System.out.println("Pedido de Bocadillo " + tipo + " realizado con éxito.");
        } else {
            BFrio.setVisible(false);
            BCaliente.setVisible(false);
            BFrio.setManaged(false);
            BCaliente.setManaged(false);
            isOrdered.setText(response.getMessage());
        }
    }
}
