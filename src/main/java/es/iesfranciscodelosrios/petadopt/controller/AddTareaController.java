package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.TareaDAO;
import es.iesfranciscodelosrios.petadopt.model.Prioridad;
import es.iesfranciscodelosrios.petadopt.model.Tarea;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddTareaController {

    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private ComboBox<Prioridad> cmbPrioridad;

    @FXML
    public void initialize() {
        cmbPrioridad.setItems(FXCollections.observableArrayList(Prioridad.values())); //poner opciones en el comboBox
    }

    @FXML
    private void guardarDatos() {
        if (validarCampos()) {
            Tarea nuevaTarea = new Tarea();
            nuevaTarea.setNombre(txtNombre.getText());
            nuevaTarea.setDescripcion(txtDescripcion.getText());
            nuevaTarea.setPrioridad(cmbPrioridad.getValue());

            if (TareaDAO.addTarea(nuevaTarea)) {
                mostrarAlerta("Éxito", "Tarea creada correctamente", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar la tarea en la base de datos", Alert.AlertType.ERROR);
            }
        }
    }

    private boolean validarCampos() {
        String error = "";
        if (txtNombre.getText().trim().isEmpty()) error += "- El nombre es obligatorio.\n";
        if (cmbPrioridad.getValue() == null) error += "- Debes elegir una prioridad.\n";

        if (error.isEmpty()) return true;

        mostrarAlerta("Campos incompletos", error, Alert.AlertType.WARNING);

        return false;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    @FXML
    private void btnSalir(ActionEvent event) {
        Utils.cerrar(event);
    }

    @FXML
    private void btnAnimales(ActionEvent event) { Utils.irA(event, "AnimalesMain.fxml", "Gestión de Animales"); }

    @FXML
    private void btnVoluntarios(ActionEvent event) { Utils.irA(event, "VoluntariosMain.fxml", "Gestión de Voluntarios"); }

    @FXML
    private void btnTareas(ActionEvent event) { Utils.irA(event, "TareasMain.fxml", "Gestión de Tareas"); }

    @FXML
    private void btnAdoptantes(ActionEvent event) { Utils.irA(event, "AdoptantesMain.fxml", "Gestión de Adoptantes"); }

    @FXML
    private void btnHistorial(ActionEvent event) { Utils.irA(event, "HistorialTareas.fxml", "Historial de Realizaciones"); }

    @FXML
    private void btnMenuPrincipal(ActionEvent event) { Utils.irA(event, "MenuPrincipal.fxml", "Menú Principal"); }


}