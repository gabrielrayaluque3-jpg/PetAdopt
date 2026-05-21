package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.RealizaDAO;
import es.iesfranciscodelosrios.petadopt.dao.TareaDAO;
import es.iesfranciscodelosrios.petadopt.dao.VoluntarioDAO;
import es.iesfranciscodelosrios.petadopt.model.Realiza;
import es.iesfranciscodelosrios.petadopt.model.Tarea;
import es.iesfranciscodelosrios.petadopt.model.Voluntario;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.ZoneId;
import java.util.Date;

public class AsignarTareaController {

    @FXML private ComboBox<Voluntario> cmbVoluntario;
    @FXML private ComboBox<Tarea> cmbTarea;
    @FXML private DatePicker dpFecha;

    @FXML
    public void initialize() {
        cmbVoluntario.getItems().addAll(VoluntarioDAO.findAllVoluntarios());
        cmbTarea.getItems().addAll(TareaDAO.findAllTareas());
        dpFecha.setValue(java.time.LocalDate.now());
    }

    @FXML
    private void guardarAsignacion() {
        Voluntario v = cmbVoluntario.getValue();
        Tarea t = cmbTarea.getValue();

        if (v != null && t != null && dpFecha.getValue() != null) {
            Date fecha = Date.from(dpFecha.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            Realiza nuevaAsignacion = new Realiza(new java.sql.Date(fecha.getTime()), false, v, t);

            if (RealizaDAO.addRealiza(nuevaAsignacion)) {
                cerrar();
            } else {
                mostrarAlerta("Error", "No se pudo asignar. Quizás ya existe esa tarea para ese voluntario en esa fecha.");
            }
        } else {
            mostrarAlerta("Atención", "Por favor, rellena todos los campos.");
        }
    }

    @FXML
    private void cerrar() {
        ((Stage) cmbTarea.getScene().getWindow()).close();
    }

    private void mostrarAlerta(String titulo, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

}