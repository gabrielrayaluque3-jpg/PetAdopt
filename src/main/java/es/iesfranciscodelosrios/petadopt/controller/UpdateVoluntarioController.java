package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.VoluntarioDAO;
import es.iesfranciscodelosrios.petadopt.model.Especialidad;
import es.iesfranciscodelosrios.petadopt.model.Voluntario;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class UpdateVoluntarioController {

    @FXML private TextField txtNombre;
    @FXML private TextField txtDni;
    @FXML private ComboBox<Especialidad> cmbEspecialidad;
    private Voluntario voluntarioAEditar;

    @FXML
    public void initialize() {
        cmbEspecialidad.setItems(FXCollections.observableArrayList(Especialidad.values()));
    }

    public void setVoluntario(Voluntario v) {
        this.voluntarioAEditar = v;
        txtNombre.setText(v.getNombre());
        txtDni.setText(v.getDni());
        cmbEspecialidad.setValue(v.getEspecialidad());
    }

    @FXML
    private void guardarCambio() {
        if (voluntarioAEditar != null) {
            voluntarioAEditar.setNombre(txtNombre.getText());
            voluntarioAEditar.setDni(txtDni.getText());
            voluntarioAEditar.setEspecialidad(cmbEspecialidad.getValue());

            if (VoluntarioDAO.updateVoluntario(voluntarioAEditar)) {
                cerrarVentana();
            } else {
                System.out.println("Error al actualizar");
            }
        }
    }

    @FXML
    private void btnCancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

}