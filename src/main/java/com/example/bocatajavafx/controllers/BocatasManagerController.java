package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Bocadillo;
import com.example.bocatajavafx.services.BocadilloService;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

import java.util.Objects;

import static com.example.bocatajavafx.controllers.MenuController.NOMBRES_DIAS;

public class BocatasManagerController {

    @FXML
    private TableView<Bocadillo> bocadillosTable;

    @FXML
    private TableColumn idColumn;
    @FXML
    private TableColumn nombreColumn;
    @FXML
    private TableColumn tipoColumn;
    @FXML
    private TableColumn diaColumn;
    @FXML
    private TableColumn costeColumn;

    private BocadilloService bocadilloService;

    public void initialize() {
        bocadilloService = new BocadilloService();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tipoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bocadillo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bocadillo, String> cellData) {
                String tipo = cellData.getValue().getTipo();
                return new SimpleStringProperty(tipo.equals("frio") ? "Frío" : "Caliente");
            }
        });
        diaColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bocadillo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bocadillo, String> cellData) {
                char diaChar = cellData.getValue().getDia();
                return new SimpleStringProperty(NOMBRES_DIAS.getOrDefault(diaChar, "Desconocido"));
            }
        });
        costeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Bocadillo, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Bocadillo, String> cellData) {
                double coste = cellData.getValue().getCoste();
                String costeEur = String.format("%.2f €", coste);
                return new SimpleStringProperty(costeEur);
            }
        });

        loadBocadillos();
    }

    private void loadBocadillos() {
        ObservableList<Bocadillo> bocadillos = FXCollections.observableArrayList(bocadilloService.getAll());
        bocadillosTable.setItems(bocadillos);
        bocadillosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void handleCreate() {
        Bocadillo newBocadillo = showBocadilloDialog(null);
        if (newBocadillo != null) {
            bocadilloService.save(newBocadillo);
            loadBocadillos();
        }
    }

    @FXML
    private void handleUpdate() {
        Bocadillo selectedBocadillo = bocadillosTable.getSelectionModel().getSelectedItem();
        if (selectedBocadillo == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un bocadillo para editar.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
            alert.showAndWait();
            return;
        }

        Bocadillo updatedBocadillo = showBocadilloDialog(selectedBocadillo);
        if (updatedBocadillo != null) {
            bocadilloService.save(updatedBocadillo);
            loadBocadillos();
        }
    }

    @FXML
    private void handleDelete() {
        Bocadillo selectedBocadillo = bocadillosTable.getSelectionModel().getSelectedItem();
        if (selectedBocadillo == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un bocadillo para eliminar.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
            alert.showAndWait();
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que deseas eliminar el bocadillo con ID: " + selectedBocadillo.getId() + "?");
        confirm.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            bocadilloService.delete(selectedBocadillo.getId());
            loadBocadillos();
        }
    }

    private Bocadillo showBocadilloDialog(Bocadillo originalBocadillo) {
        Dialog<Bocadillo> dialog = new Dialog<>();
        dialog.setTitle(originalBocadillo == null ? "Crear Bocadillo" : "Editar Bocadillo");
        dialog.setHeaderText(originalBocadillo == null ? "Ingresa los detalles del nuevo bocadillo:" : "Actualiza los detalles del bocadillo:");

        ButtonType saveButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        dialog.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField nombreField = new TextField();
        nombreField.setText(originalBocadillo != null ? originalBocadillo.getNombre() : "");

        ComboBox<String> tipoComboBox = new ComboBox<>();
        tipoComboBox.getItems().addAll("frio", "caliente");
        tipoComboBox.setValue(originalBocadillo != null ? originalBocadillo.getTipo() : "frio");

        TextField ingredientesField = new TextField();
        ingredientesField.setText(originalBocadillo != null ? originalBocadillo.getIngredientes() : "");

        TextField costeField = new TextField();
        costeField.setText(originalBocadillo != null ? String.valueOf(originalBocadillo.getCoste()) : "0.0");

        ComboBox<Character> diaComboBox = new ComboBox<>();
        diaComboBox.getItems().addAll('L', 'M', 'X', 'J', 'V');
        diaComboBox.setValue(originalBocadillo != null ? originalBocadillo.getDia() : 'L');

        TextField menuField = new TextField();
        menuField.setText(originalBocadillo != null ? String.valueOf(originalBocadillo.getMenu()) : "1");

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Tipo:"), 0, 1);
        grid.add(tipoComboBox, 1, 1);
        grid.add(new Label("Ingredientes:"), 0, 2);
        grid.add(ingredientesField, 1, 2);
        grid.add(new Label("Coste (€):"), 0, 3);
        grid.add(costeField, 1, 3);
        grid.add(new Label("Día:"), 0, 4);
        grid.add(diaComboBox, 1, 4);
        grid.add(new Label("Menú:"), 0, 5);
        grid.add(menuField, 1, 5);

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> nombreField.requestFocus());
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String nombre = nombreField.getText().trim();
                    String tipo = tipoComboBox.getValue();
                    String ingredientes = ingredientesField.getText().trim();
                    float coste = Float.parseFloat(costeField.getText().trim());
                    char dia = diaComboBox.getValue();
                    int menu = Integer.parseInt(menuField.getText().trim());

                    if (nombre.isEmpty() || tipo == null || ingredientes.isEmpty() || coste <= 0 || dia == 0 || menu <= 0) {
                        throw new IllegalArgumentException("Todos los campos son obligatorios.");
                    }

                    Bocadillo bocadillo = new Bocadillo(
                            originalBocadillo != null ? originalBocadillo.getId() : 0,
                            nombre,
                            tipo,
                            ingredientes,
                            coste,
                            dia,
                            menu
                    );
                    return bocadillo;
                } catch (NumberFormatException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "El valor del coste o menú debe ser numérico.", ButtonType.OK);
                    alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
                    alert.showAndWait();
                    return null;
                } catch (IllegalArgumentException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }
}