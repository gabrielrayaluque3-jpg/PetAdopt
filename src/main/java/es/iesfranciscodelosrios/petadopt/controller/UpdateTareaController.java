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

public class UpdateTareaController {

    @FXML private TextField txtNombre;
    @FXML private TextArea txtDescripcion;
    @FXML private ComboBox<Prioridad> cmbPrioridad;
    private Tarea tareaAEditar;

    @FXML
    public void initialize() {
        cmbPrioridad.setItems(FXCollections.observableArrayList(Prioridad.values()));
    }

    public void setTarea(Tarea t) {
        this.tareaAEditar = t;
        txtNombre.setText(t.getNombre());
        txtDescripcion.setText(t.getDescripcion());
        cmbPrioridad.setValue(t.getPrioridad());
    }

    @FXML
    private void guardarCambio() {
        if (tareaAEditar != null) {
            tareaAEditar.setNombre(txtNombre.getText());
            tareaAEditar.setDescripcion(txtDescripcion.getText());
            tareaAEditar.setPrioridad(cmbPrioridad.getValue());

            if (TareaDAO.updateTarea(tareaAEditar)) {
                cerrarVentana();
            } else {
                System.out.println("Error al actualizar la tarea en la BBDD");
            }
        }
    }

    @FXML
    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

}