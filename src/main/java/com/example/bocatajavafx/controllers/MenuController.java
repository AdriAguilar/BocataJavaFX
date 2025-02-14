package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.services.BocadilloService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MenuController {

    @FXML
    private ListView menu;

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

        // Agrupar los bocadillos por menú (semana) y por día de la semana
        Map<Integer, Map<Character, List<Bocadillo>>> bocadillosPorMenuYDia = bocadillos.stream()
                .collect(Collectors.groupingBy(
                        Bocadillo::getMenu,
                        Collectors.groupingBy(Bocadillo::getDia)
                ));

        ObservableList<String> menuList = FXCollections.observableArrayList();

        for (Map.Entry<Integer, Map<Character, List<Bocadillo>>> entryMenu : bocadillosPorMenuYDia.entrySet()) {
            Integer menuNumber = entryMenu.getKey();
            menuList.add("Menú Semana " + menuNumber);

            Map<Character, List<Bocadillo>> bocadillosPorDia = entryMenu.getValue();

            for (Character dia : DIAS) {
                List<Bocadillo> bocadillosDia = bocadillosPorDia.getOrDefault(dia, List.of());
                String nombreDia = NOMBRES_DIAS.get(dia);
                menuList.add("  " + nombreDia);

                for (Bocadillo bocadillo : bocadillosDia) {
                    String tipo = bocadillo.getTipo().equals("frio") ? "Frío" : "Caliente";
                    menuList.add("    " + bocadillo.getNombre() + " - " + tipo + " - " + bocadillo.getCoste() + "€");
                }
            }
        }

        // Configurar ListView para mostrar cada elemento
        menu.setItems(menuList);
        menu.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    VBox vbox = new VBox();
                    Text text = new Text(item);
                    vbox.getChildren().add(text);
                    setGraphic(vbox);
                }
            }
        });
    }
}
