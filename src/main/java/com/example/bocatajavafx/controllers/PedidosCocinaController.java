package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Pedido;
import com.example.bocatajavafx.services.PedidoService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class PedidosCocinaController {
    @FXML
    private ListView pedidos;

    public void initialize() {
        PedidoService pedidoService = new PedidoService();
        List<Pedido> listaPedidos = pedidoService.getTodayPedidos();
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
                            setText("Pedido ID: " + item.getId() + "  -  " + item.getBocadillo().getNombre() +
                                    "  -  " + item.getAlumno().getNombre() +
                                    "  -  Curso: " + item.getAlumno().getCurso().getNombre() +
                                    "  -  Total: " + item.getCosteTotal() + "€"
                            );
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }
}
