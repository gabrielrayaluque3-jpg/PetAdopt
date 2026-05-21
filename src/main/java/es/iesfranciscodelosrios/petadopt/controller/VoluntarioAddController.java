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

public class VoluntarioAddController {
    @FXML
    public Button btnGuardar;
    @FXML
    public Button btnCancelar;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDni;
    @FXML
    private ComboBox<Especialidad> cmbEspecialidad;

    @FXML
    public void initialize() {
        cmbEspecialidad.setItems(FXCollections.observableArrayList(Especialidad.values())); //Para rellenar el menu desplegable con los datos del enum de especialidad
    }

    @FXML
    private void guardarDatos() {
        if (datosValidos()&&comprobarDNI()) {
            Voluntario v = new Voluntario(
                    0,
                    txtNombre.getText(),
                    txtDni.getText(),
                    cmbEspecialidad.getValue()
            );
            if (VoluntarioDAO.addVoluntario(v)) {
                mostrarAlerta("Exito", "Voluntario añadido correctamente", Alert.AlertType.INFORMATION);
                cerrarVentana();
            } else {
                mostrarAlerta("Error", "No se pudo guardar en la base de datos", Alert.AlertType.ERROR);
            }
        }
    }



    private boolean datosValidos() {
        boolean valido = false;
        String errorMsg = "";
        if (txtNombre.getText().isEmpty()){
            errorMsg += "Nombre obligatorio\n";
        }
        if (txtDni.getText().isEmpty()){
            errorMsg += "DNI obligatorio\n";
        }
        if (cmbEspecialidad.getValue() == null){
            errorMsg += "Especialidad obligatoria\n";
        }

        if (errorMsg.isEmpty()){
             return true;
        }
        mostrarAlerta("Campos inválidos", errorMsg, Alert.AlertType.WARNING);
        return valido;

    }

    public boolean comprobarDNI(){
        String dniIntroducido = txtDni.getText().trim();

        if (!Utils.validarDNI(dniIntroducido)) {
            mostrarAlerta("DNI Incorrecto", "El DNI introducido no es válido.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void mostrarAlerta(String titulo, String contenido, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void cancelar(){
        cerrarVentana();
    }

    @FXML
    private void onSalirClick(ActionEvent event) {
        Utils.cerrar(event);
    }

    @FXML
    private void abrirAnimales(ActionEvent event) { Utils.irA(event, "AnimalesMain.fxml", "Gestión de Animales"); }

    @FXML
    private void abrirVoluntarios(ActionEvent event) { Utils.irA(event, "VoluntariosMain.fxml", "Gestión de Voluntarios"); }

    @FXML
    private void abrirTareas(ActionEvent event) { Utils.irA(event, "TareasMain.fxml", "Gestión de Tareas"); }

    @FXML
    private void abrirAdoptantes(ActionEvent event) { Utils.irA(event, "AdoptantesMain.fxml", "Gestión de Adoptantes"); }

    @FXML
    private void abrirHistorial(ActionEvent event) { Utils.irA(event, "HistorialTareas.fxml", "Historial de Realizaciones"); }

    @FXML
    private void abrirMenuPrincipal(ActionEvent event) { Utils.irA(event, "MenuPrincipal.fxml", "Menú Principal"); }
}