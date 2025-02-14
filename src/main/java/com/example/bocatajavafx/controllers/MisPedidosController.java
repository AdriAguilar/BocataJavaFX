package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.models.Pedido;
import com.example.bocatajavafx.services.AlumnoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.util.List;

public class MisPedidosController {

    @FXML
    public ListView pedidos;

    public void initialize() {
        AlumnoService alumnoService = new AlumnoService();
        List<Pedido> listaPedidos = alumnoService.getAlumnoPedidos(MainController.getAlumno().getNia());
        ObservableList<Pedido> pedidoObservable = FXCollections.observableArrayList(listaPedidos);

        pedidos.setItems(pedidoObservable);
        pedidos.setCellFactory(new Callback<ListView<Pedido>, ListCell<Pedido>>() {
            @Override
            public ListCell<Pedido> call(ListView<Pedido> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(Pedido item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText("Pedido ID: " + item.getId() + "  -  " + item.getBocadillo().getNombre() + "  -  Fecha: " + item.getFecha() + "  -  Total: " + item.getCosteTotal() + "€");
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }
}
