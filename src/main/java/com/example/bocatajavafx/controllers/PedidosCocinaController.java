package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.models.Pedido;
import com.example.bocatajavafx.services.PedidoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.util.List;

public class PedidosCocinaController {
    @FXML
    private TableView<Pedido> pedidosTable;

    @FXML
    private TableColumn cid;

    @FXML
    private TableColumn cbocata;

    @FXML
    private TableColumn calumno;

    @FXML
    private TableColumn ccurso;

    @FXML
    private TableColumn cfecha;

    @FXML
    private TableColumn cestado;

    @FXML
    private TableColumn ctotal;

    public void initialize() {
        PedidoService pedidoService = new PedidoService();
        List<Pedido> listaPedidos = pedidoService.getTodayPedidos();
        ObservableList<Pedido> pedidoObservable = FXCollections.observableArrayList(listaPedidos);

        cid.setCellValueFactory(new PropertyValueFactory<>("id"));
        cbocata.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> cellData) {
                String bocata = cellData.getValue().getBocadillo().getNombre();
                return new SimpleStringProperty(bocata);
            }
        });
        calumno.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> cellData) {
                String alumno = cellData.getValue().getAlumno().getNombre();
                return new SimpleStringProperty(alumno);
            }
        });
        ccurso.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> cellData) {
                String curso = cellData.getValue().getAlumno().getCurso().getNombre();
                return new SimpleStringProperty(curso);
            }
        });
        cfecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        cestado.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> cellData) {
                String estado = cellData.getValue().getEstado();
                if (estado != null && !estado.isEmpty()) {
                    estado = Character.toUpperCase(estado.charAt(0)) + estado.substring(1).toLowerCase();
                }
                return new SimpleStringProperty(estado);
            }
        });
        ctotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedido, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedido, String> cellData) {
                double costeTotal = cellData.getValue().getCosteTotal();
                String total = String.format("%.2f €", costeTotal);
                return new SimpleStringProperty(total);
            }
        });

        pedidosTable.setItems(pedidoObservable);
        pedidosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}
