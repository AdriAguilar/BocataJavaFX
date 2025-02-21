package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.services.BocadilloService;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.*;
import java.util.stream.Collectors;

public class MenuController {

    @FXML
    private TableView<Bocadillo> menuTable;

    @FXML
    private TableColumn cnombre;

    @FXML
    private TableColumn ctipo;

    @FXML
    private TableColumn cdia;

    @FXML
    private TableColumn cmenu;

    @FXML
    private TableColumn ccoste;

    private static final List<Character> DIAS = List.of('L', 'M', 'X', 'J', 'V');
    private static final Map<Character, String> NOMBRES_DIAS = Map.of(
            'L', "Lunes",
            'M', "Martes",
            'X', "Miércoles",
            'J', "Jueves",
            'V', "Viernes"
    );

    public void initialize() {
        BocadilloService bocadilloService = new BocadilloService();
        List<Bocadillo> bocadillos = bocadilloService.getAll();

        Map<Integer, Map<Character, List<Bocadillo>>> bocadillosPorMenuYDia = bocadillos.stream()
                .collect(Collectors.groupingBy(
                        Bocadillo::getMenu,
                        Collectors.groupingBy(Bocadillo::getDia)
                ));

        ObservableList<Bocadillo> bocadilloList = FXCollections.observableArrayList();

        for (Map.Entry<Integer, Map<Character, List<Bocadillo>>> entryMenu : bocadillosPorMenuYDia.entrySet()) {
            Map<Character, List<Bocadillo>> bocadillosPorDia = entryMenu.getValue();

            for (Character dia : DIAS) {
                List<Bocadillo> bocadillosDia = bocadillosPorDia.getOrDefault(dia, List.of());
                for (Bocadillo bocadillo : bocadillosDia) {
                    bocadilloList.add(bocadillo);
                }
            }
        }

        cnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        ctipo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bocadillo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bocadillo, String> cellData) {
                String tipo = cellData.getValue().getTipo();
                return new SimpleStringProperty(tipo.equals("frio") ? "Frío" : "Caliente");
            }
        });

        cdia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bocadillo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bocadillo, String> cellData) {
                char diaChar = cellData.getValue().getDia();
                return new SimpleStringProperty(NOMBRES_DIAS.getOrDefault(diaChar, "Desconocido"));
            }
        });

        cmenu.setCellValueFactory(new PropertyValueFactory<>("menu"));

        ccoste.setCellValueFactory(new PropertyValueFactory<>("coste"));

        menuTable.getColumns().addAll(cnombre, ctipo, cdia, cmenu, ccoste);

        menuTable.setItems(bocadilloList);

        menuTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
}