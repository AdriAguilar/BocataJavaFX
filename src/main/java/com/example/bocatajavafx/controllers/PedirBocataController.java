package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.services.BocadilloResponse;
import com.example.bocatajavafx.services.BocadilloService;
import com.example.bocatajavafx.services.PedidoService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class PedirBocataController {
    @FXML
    private Button BFrio;

    @FXML
    private Button BCaliente;

    @FXML
    private Label isOrdered;

    @FXML
    private TableView<Bocadillo> bocatasTable;

    @FXML
    private TableColumn cnombre;

    @FXML
    private TableColumn ctipo;

    @FXML
    private TableColumn ccoste;

    private PedidoService pedidoService;
    private BocadilloService bocadilloService;

    public void initialize() {
        pedidoService = new PedidoService();
        bocadilloService = new BocadilloService();

        setupBocadillosTable();

        BFrio.setOnAction(event -> handleBocata("frio"));
        BCaliente.setOnAction(event -> handleBocata("caliente"));
    }

    private void setupBocadillosTable() {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        char diaChar = getDiaChar(today);
        List<Bocadillo> bocadillosHoy = bocadilloService.getTodayBocadillos(diaChar, 1);
        ObservableList<Bocadillo> bocadillosObservable = FXCollections.observableArrayList(bocadillosHoy);

        cnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        ctipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bocadillo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bocadillo, String> cellData) {
                String tipo = cellData.getValue().getTipo();
                return new SimpleStringProperty(tipo.equals("frio") ? "Frío" : "Caliente");
            }
        });
        ccoste.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bocadillo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bocadillo, String> cellData) {
                double coste = cellData.getValue().getCoste();
                String costeEur = String.format("%.2f €", coste);
                return new SimpleStringProperty(costeEur);
            }
        });

        bocatasTable.setItems(bocadillosObservable);
        bocatasTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

    private char getDiaChar(DayOfWeek dayOfWeek) {
        return switch (dayOfWeek) {
            case MONDAY -> 'L';
            case TUESDAY -> 'M';
            case WEDNESDAY -> 'X';
            case THURSDAY -> 'J';
            case FRIDAY -> 'V';
            default -> ' ';
        };
    }
}
