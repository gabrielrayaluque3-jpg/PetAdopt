package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.Ejecutable;
import es.iesfranciscodelosrios.petadopt.dao.VoluntarioDAO;
import es.iesfranciscodelosrios.petadopt.model.Voluntario;
import es.iesfranciscodelosrios.petadopt.model.Especialidad;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VoluntariosMainController {

    @FXML
    private TextField txtBuscador;
    @FXML
    private TableView<Voluntario> tblVoluntarios;
    @FXML
    private TableColumn<Voluntario, Integer> colId;
    @FXML
    private TableColumn<Voluntario, String> colNombre;
    @FXML
    private TableColumn<Voluntario, String> colDni;
    @FXML
    private TableColumn<Voluntario, Especialidad> colEspecialidad;

    private ObservableList<Voluntario> listaVoluntarios;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDni.setCellValueFactory(new PropertyValueFactory<>("dni"));
        colEspecialidad.setCellValueFactory(new PropertyValueFactory<>("especialidad"));

        cargarDatos();
    }

    private void cargarDatos() {
        List<Voluntario> bdList = VoluntarioDAO.findAllVoluntarios();
        listaVoluntarios = FXCollections.observableArrayList(bdList);
        tblVoluntarios.setItems(listaVoluntarios);
    }

    @FXML
    private void eliminarVoluntario() {
        Voluntario seleccionado = tblVoluntarios.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmar eliminación");
            alert.setHeaderText("¿Estás seguro de que quieres eliminar a " + seleccionado.getNombre() + "?");
            alert.setContentText("Esta acción no se puede deshacer.");

            if (alert.showAndWait().get() == ButtonType.OK) {
                if (VoluntarioDAO.deleteVoluntario(seleccionado.getId())) {
                    listaVoluntarios.remove(seleccionado);
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar de la base de datos.", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Atención", "Por favor, selecciona un voluntario de la tabla.", Alert.AlertType.WARNING);
        }
    }

    private void mostrarAlerta(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private String filtroActual = "Nombre";

    @FXML
    private void filtroAll() {
        filtroActual = "Todos";
        txtBuscador.setText("");
        cargarDatos();
    }

    @FXML
    private void filtroId() {
        filtroActual = "Id";
        txtBuscador.setDisable(false);
        txtBuscador.setPromptText("Escribe el ID...");
    }

    @FXML
    private void filtroNombre() {
        filtroActual = "Nombre";
        txtBuscador.setDisable(false);
        txtBuscador.setPromptText("Escribe el nombre...");
    }

    @FXML
    private void filtroEspecialidad() {
        filtroActual = "Especialidad";
        txtBuscador.setDisable(false);
        txtBuscador.setPromptText("Escribe la especialidad...");
    }

    @FXML
    private void filtrarLista() {
        String texto = txtBuscador.getText();
        List<Voluntario> resultados = new ArrayList<>();

        if (texto.isEmpty()) {
            cargarDatos();
            return;
        }

        switch (filtroActual) {
            case "Id":
                try {
                    int id = Integer.parseInt(texto);
                    Voluntario v = VoluntarioDAO.findVoluntarioById(id);
                    if (v != null) resultados.add(v);
                } catch (NumberFormatException e) {

                }
                break;

            case "Nombre":
                resultados = VoluntarioDAO.findByContainName(texto);
                break;

            case "Especialidad":
                try {
                    Especialidad esp = Especialidad.valueOf(texto.toUpperCase());
                    resultados = VoluntarioDAO.findByEspecialidad(esp);
                } catch (IllegalArgumentException e) {

                }
                break;
        }

        listaVoluntarios.setAll(resultados);
    }

    @FXML
    private void addVoluntario() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Ejecutable.class.getResource("/es/iesfranciscodelosrios/petadopt/AddVoluntario.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Añadir Voluntario");
        stage.setScene(scene);
        stage.showAndWait();
        cargarDatos();
    }

    @FXML
    private void UpdateVoluntario() throws IOException {
        Voluntario seleccionado = tblVoluntarios.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Ejecutable.class.getResource("UpdateVoluntario.fxml"));
            Parent root = fxmlLoader.load();

            UpdateVoluntarioController controller = fxmlLoader.getController();
            controller.setVoluntario(seleccionado);

            Stage stage = new Stage();
            stage.setTitle("Editando a " + seleccionado.getNombre());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            cargarDatos();

        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Por favor, selecciona un voluntario de la lista para editar.");
            alert.show();
        }
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
    private void onSalirClick(ActionEvent event) { Utils.cerrar(event); }

}