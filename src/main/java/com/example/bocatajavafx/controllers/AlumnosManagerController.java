package com.example.bocatajavafx.controllers;

import com.example.bocatajavafx.models.Alumno;
import com.example.bocatajavafx.models.Curso;
import com.example.bocatajavafx.services.AlumnoService;
import com.example.bocatajavafx.util.ValidatorUtil;
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
import javafx.util.StringConverter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class AlumnosManagerController {
    @FXML
    private TableView<Alumno> alumnosTable;
    @FXML
    private TableColumn niaColumn;
    @FXML
    private TableColumn nombreColumn;
    @FXML
    private TableColumn emailColumn;
    @FXML
    private TableColumn cursoColumn;
    @FXML
    private TableColumn activoColumn;
    @FXML
    private TableColumn fechaColumn;
    @FXML
    private TableColumn motivoColumn;

    private AlumnoService alumnoService;

    public void initialize() {
        alumnoService = new AlumnoService();

        niaColumn.setCellValueFactory(new PropertyValueFactory<>("nia"));
        nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("correo"));
        cursoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumno, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Alumno, String> cellData) {
                String curso = cellData.getValue().getCurso().getNombre();
                return new SimpleStringProperty(curso);
            }
        });
        activoColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Alumno, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Alumno, String> cellData) {
                boolean activo = cellData.getValue().isActivo();
                return new SimpleStringProperty(activo ? "Activo" : "Inactivo");
            }
        });
        fechaColumn.setCellValueFactory(new PropertyValueFactory<>("fechaBaja"));
        motivoColumn.setCellValueFactory(new PropertyValueFactory<>("motivoBaja"));

        loadAlumnos();
    }

    private void loadAlumnos() {
        ObservableList<Alumno> alumnos = FXCollections.observableArrayList(alumnoService.getAll());
        alumnosTable.setItems(alumnos);
        alumnosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    private void handleCreate() {
        Alumno nuevoAlumno = showAlumnoDialog(null);
        if (nuevoAlumno != null) {
            alumnoService.save(nuevoAlumno);
            loadAlumnos();
        }
    }

    @FXML
    private void handleUpdate() {
        Alumno selectedAlumno = alumnosTable.getSelectionModel().getSelectedItem();
        if (selectedAlumno == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un alumno para editar.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
            alert.showAndWait();
            return;
        }

        Alumno updatedAlumno = showAlumnoDialog(selectedAlumno);
        if (updatedAlumno != null) {
            alumnoService.save(updatedAlumno);
            loadAlumnos();
        }
    }

    @FXML
    private void handleDisable() {
        Alumno selectedAlumno = alumnosTable.getSelectionModel().getSelectedItem();
        if (selectedAlumno == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un alumno para desactivar.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
            alert.showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Desactivar Alumno");
        dialog.setHeaderText("Ingresa el motivo de la baja:");
        dialog.setContentText("Motivo:");

        dialog.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String motivo = result.get();
            if (!motivo.trim().isEmpty()) {
                Date today = Date.valueOf(LocalDate.now());
                alumnoService.desactivar(selectedAlumno.getNia(), today, motivo);
                loadAlumnos();
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "El motivo no puede estar vacío.");
                alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleActivate() {
        Alumno selectedAlumno = alumnosTable.getSelectionModel().getSelectedItem();
        if (selectedAlumno == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Selecciona un alumno para activar.");
            alert.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
            alert.showAndWait();
            return;
        }

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Desactivar Alumno");
        dialog.setHeaderText("Ingresa el motivo de la baja:");
        dialog.setContentText("Motivo:");

        dialog.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Estás seguro de que deseas activar al alumno con ID: " + selectedAlumno.getNia() + "?");
        confirm.getDialogPane().setId("alert-confirm-activate");
        confirm.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());
        if (confirm.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            alumnoService.activar(selectedAlumno.getNia());
            loadAlumnos();
        }
    }

    private Alumno showAlumnoDialog(Alumno originalAlumno) {
        Dialog<Alumno> dialog = new Dialog<>();
        dialog.setTitle(originalAlumno == null ? "Crear Alumno" : "Editar Alumno");
        dialog.setHeaderText(originalAlumno == null ? "Ingresa los detalles del nuevo alumno:" : "Actualiza los detalles del alumno:");

        dialog.getDialogPane().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/bocatajavafx/fxml/css/crud-panels.css")).toExternalForm());

        ButtonType saveButtonType = new ButtonType("Guardar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField niaField = new TextField();
        niaField.setText(String.valueOf(originalAlumno != null ? originalAlumno.getNia() : ""));

        TextField nombreField = new TextField();
        nombreField.setText(originalAlumno != null ? originalAlumno.getNombre() : "");

        TextField emailField = new TextField();
        emailField.setText(originalAlumno != null ? originalAlumno.getCorreo() : "");

        PasswordField pwField = new PasswordField();
        pwField.setText("");

        ComboBox<Curso> cursoComboBox = new ComboBox<>();
        List<Curso> cursos = alumnoService.getCursos();
        cursoComboBox.setCellFactory(param -> new ListCell<Curso>() {
            @Override
            protected void updateItem(Curso curso, boolean empty) {
                super.updateItem(curso, empty);
                if (curso == null || empty) {
                    setText(null);
                } else {
                    setText(curso.getNombre());
                }
            }
        });
        cursoComboBox.setConverter(new StringConverter<Curso>() {
            @Override
            public String toString(Curso curso) {
                return curso != null ? curso.getNombre() : "";
            }

            @Override
            public Curso fromString(String string) {
                return null;
            }
        });
        cursoComboBox.getItems().setAll(cursos);
        if (originalAlumno != null) {
            Curso cursoSeleccionado = cursos.stream()
                    .filter(c -> c.getNombre().equals(originalAlumno.getCurso().getNombre()))
                    .findFirst()
                    .orElse(null);
            cursoComboBox.setValue(cursoSeleccionado);
        }

        TextField motivoField = new TextField();
        DatePicker fechaPicker = new DatePicker();

        if (originalAlumno != null && !originalAlumno.isActivo()) {
            motivoField.setText(originalAlumno.getMotivoBaja() != null ? originalAlumno.getMotivoBaja() : "");
            LocalDate fechaBaja = originalAlumno.getFechaBaja() != null ? originalAlumno.getFechaBaja().toLocalDate() : null;
            fechaPicker.setValue(fechaBaja);
        }

        grid.add(new Label("NIA:"), 0, 0);
        grid.add(niaField, 1, 0);

        grid.add(new Label("Nombre:"), 0, 1);
        grid.add(nombreField, 1, 1);

        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);

        grid.add(new Label("Contraseña:"), 0, 3);
        grid.add(pwField, 1, 3);

        grid.add(new Label("Curso:"), 0, 4);
        grid.add(cursoComboBox, 1, 4);

        if (originalAlumno != null && !originalAlumno.isActivo()) {
            grid.add(new Label("Motivo de Baja:"), 0, 5);
            grid.add(motivoField, 1, 5);

            grid.add(new Label("Fecha de Baja:"), 0, 6);
            grid.add(fechaPicker, 1, 6);
        }

        dialog.getDialogPane().setContent(grid);

        Platform.runLater(() -> niaField.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    int nia = Integer.parseInt(niaField.getText().trim());
                    String nombre = nombreField.getText().trim();
                    String email = emailField.getText().trim();
                    String password = pwField.getText().trim();
                    Curso curso = cursoComboBox.getValue();
                    String motivo = motivoField.getText().trim();
                    LocalDate fechaBaja = fechaPicker.getValue();

                    if (nia == 0 || nombre.isEmpty() || email.isEmpty() || curso == null) {
                        throw new IllegalArgumentException("Todos los campos son obligatorios.");
                    }

                    String hashedPw = "";
                    if (!password.isEmpty()) {
                        hashedPw = ValidatorUtil.hashPassword(password);
                    }

                    Alumno alumno = new Alumno(
                            nia,
                            nombre,
                            email,
                            originalAlumno != null ? originalAlumno.getContrasena() : hashedPw,
                            curso
                    );

                    if (originalAlumno != null && !originalAlumno.isActivo()) {
                        alumno.setMotivoBaja(motivo.isEmpty() ? null : motivo);
                        alumno.setFechaBaja(fechaBaja != null ? Date.valueOf(fechaBaja) : null);
                    }

                    return alumno;
                } catch (IllegalArgumentException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }
}
