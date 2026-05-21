package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.*;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuPrincipalController {

    @FXML private Label lblTotalAnimales;
    @FXML private Label lblTotalVoluntarios;
    @FXML private Label lblTareasPendientes;

    @FXML
    public void initialize() {
        cargarDatosEstadisticos();
    }

    private void cargarDatosEstadisticos() {
        int totalA = AnimalDAO.findAllAnimales().size();
        lblTotalAnimales.setText(String.valueOf(totalA));

        int totalV = VoluntarioDAO.findAllVoluntarios().size();
        lblTotalVoluntarios.setText(String.valueOf(totalV));

        int pendientes = RealizaDAO.findByCompletada(false).size();
        lblTareasPendientes.setText(String.valueOf(pendientes));
    }

    @FXML
    private void abrirAnimales() throws IOException {
        cambiarEscena("/es/iesfranciscodelosrios/petadopt/AnimalesMain.fxml", "Gestión de Animales");
    }

    @FXML
    private void abrirVoluntarios() throws IOException {
        cambiarEscena("/es/iesfranciscodelosrios/petadopt/VoluntariosMain.fxml", "Gestión de Voluntarios");
    }

    @FXML
    private void abrirTareas() throws IOException {
        cambiarEscena("/es/iesfranciscodelosrios/petadopt/TareasMain.fxml", "Gestión de Tareas");
    }

    @FXML
    private void abrirAdoptantes() throws IOException {
        cambiarEscena("/es/iesfranciscodelosrios/petadopt/AdoptantesMain.fxml", "Gestión de Adoptantes");
    }

    private void cambiarEscena(String fxml, String titulo) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) lblTotalAnimales.getScene().getWindow();
        stage.setTitle(titulo);
        stage.setScene(scene);
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

    @FXML
    private void btnSalir(ActionEvent event) { Utils.cerrar(event); }
}