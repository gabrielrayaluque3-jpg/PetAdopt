package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.Ejecutable;
import es.iesfranciscodelosrios.petadopt.dao.TareaDAO;
import es.iesfranciscodelosrios.petadopt.model.Tarea;
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

public class TareasMainController {

    @FXML private TableView<Tarea> tblTareas;
    @FXML private TableColumn<Tarea, Integer> colId;
    @FXML private TableColumn<Tarea, String> colNombre; // Nueva columna
    @FXML private TableColumn<Tarea, String> colDescripcion;
    @FXML private TableColumn<Tarea, String> colPrioridad;
    @FXML private TextField txtBuscador;
    @FXML private MenuButton menuFiltro;

    private ObservableList<Tarea> listaTareas;
    private String filtroActual = "Nombre";

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrioridad.setCellValueFactory(new PropertyValueFactory<>("prioridad"));

        cargarDatos();
    }

    private void cargarDatos() {
        TareaDAO.findAllTareas();
        listaTareas = FXCollections.observableArrayList(TareaDAO.findAllTareas());
        tblTareas.setItems(listaTareas);
    }

    @FXML
    private void filtrarTareas() {
        String textoBusqueda = txtBuscador.getText().toLowerCase();

        if (textoBusqueda == null || textoBusqueda.isEmpty()) {
            tblTareas.setItems(listaTareas);
            return;
        }

        ObservableList<Tarea> listaFiltrada = FXCollections.observableArrayList();

        for (Tarea t : listaTareas) {
            switch (filtroActual) {
                case "Nombre":
                    if (t.getNombre().toLowerCase().contains(textoBusqueda)) {
                        listaFiltrada.add(t);
                    }
                    break;

                case "Descripción":
                    if (t.getDescripcion().toLowerCase().contains(textoBusqueda)) {
                        listaFiltrada.add(t);
                    }
                    break;

                case "Prioridad":
                    String prioridadTexto = t.getPrioridad().name().toLowerCase();
                    if (prioridadTexto.contains(textoBusqueda)) {
                        listaFiltrada.add(t);
                    }
                    break;
            }
        }

        tblTareas.setItems(listaFiltrada);
    }

    @FXML
    private void btnAddTarea() throws IOException {
        abrirVentana("AddTarea.fxml", "Nueva Tarea");
    }

    @FXML
    private void btnEliminar() {
        Tarea seleccionada = tblTareas.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Confirmar eliminación");
            confirmacion.setHeaderText("¿Vas a borrar la tarea: " + seleccionada.getNombre() + "?");
            confirmacion.setContentText("Esta acción eliminará la tarea de la base de datos permanentemente.");

            if (confirmacion.showAndWait().get() == ButtonType.OK) {

                if (TareaDAO.deleteTarea(seleccionada.getId())) {

                    listaTareas.remove(seleccionada);
                } else {
                    mostrarAlerta("Error", "No se puede eliminar la tarea. Comprueba si está asignada a algún voluntario.", Alert.AlertType.ERROR);
                }
            }
        } else {
            mostrarAlerta("Atención", "Debes seleccionar una tarea de la tabla para poder eliminarla.", Alert.AlertType.WARNING);
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @FXML
    private void btnHistorial() throws IOException {
        abrirVentana("HistorialTareas.fxml", "Historial de Tareas Realizadas");
    }

    @FXML private void filtroNombre() { filtroActual = "Nombre"; menuFiltro.setText("Nombre"); }
    @FXML private void filtroDescripcion() { filtroActual = "Descripción"; menuFiltro.setText("Descripción"); }
    @FXML private void filtroPrioridad() { filtroActual = "Prioridad"; menuFiltro.setText("Prioridad"); }

    private void abrirVentana(String fxml, String titulo) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Ejecutable.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle(titulo);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        cargarDatos();
    }

    @FXML private void btnSalir() {
        ((Stage) tblTareas.getScene().getWindow()).close();
    }

    @FXML
    private void btnEditar() throws IOException {
        Tarea seleccionada = tblTareas.getSelectionModel().getSelectedItem();

        if (seleccionada != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(Ejecutable.class.getResource("UpdateTarea.fxml"));
            Parent root = fxmlLoader.load();
            UpdateTareaController controller = fxmlLoader.getController();
            controller.setTarea(seleccionada);
            Stage stage = new Stage();
            stage.setTitle("Editar Tarea: " + seleccionada.getNombre());
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            cargarDatos();
        } else {
            mostrarAlerta("Atención", "Por favor, selecciona una tarea de la lista.", Alert.AlertType.WARNING);
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
    private void btnSalir(ActionEvent event) { Utils.cerrar(event); }

}