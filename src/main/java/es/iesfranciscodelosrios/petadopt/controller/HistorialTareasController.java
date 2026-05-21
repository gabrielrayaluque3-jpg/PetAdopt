package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.RealizaDAO;
import es.iesfranciscodelosrios.petadopt.model.Realiza;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class HistorialTareasController {

    @FXML private TableView<Realiza> tblHistorial;
    @FXML private TableColumn<Realiza, String> colIdTarea;
    @FXML private TableColumn<Realiza, String> colVoluntario;
    @FXML private TableColumn<Realiza, String> colFecha;
    @FXML private TableColumn<Realiza, RadioButton> colCompletada;
    @FXML private TextField txtBuscador;
    @FXML private ComboBox<String> cmbFiltroEstado;
    private ObservableList<Realiza> listaRealizaciones;

    @FXML
    public void initialize() {
        colIdTarea.setCellValueFactory(data -> new SimpleStringProperty(String.valueOf(data.getValue().getTarea().getId())));
        colVoluntario.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getVoluntario().getNombre()));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        colCompletada.setCellValueFactory(data -> {
            Realiza realiza = data.getValue();
            RadioButton rb = new RadioButton();

            rb.setSelected(realiza.isCompletada());

            rb.setDisable(false);

            rb.setOnAction(event -> {
                realiza.setCompletada(rb.isSelected());

                boolean exito = RealizaDAO.updateRealiza(realiza);

                if (!exito) {
                    rb.setSelected(!rb.isSelected());
                    realiza.setCompletada(rb.isSelected());
                    System.err.println("Error al actualizar el estado en la BD");
                }
            });

            return new SimpleObjectProperty<>(rb);
        });

        cmbFiltroEstado.setItems(FXCollections.observableArrayList("Todos", "Completadas", "Pendientes"));
        cmbFiltroEstado.setValue("Todos");

        cargarDatos();
    }

    private void cargarDatos() {
        listaRealizaciones = FXCollections.observableArrayList(RealizaDAO.findAllRealizaciones());
        tblHistorial.setItems(listaRealizaciones);
    }

    @FXML
    private void filtrarHistorial() {
        String query = txtBuscador.getText().toLowerCase();

        if (query.isEmpty()) {
            tblHistorial.setItems(listaRealizaciones);
            return;
        }

        List<Realiza> filtrada = listaRealizaciones.stream()
                .filter(r -> String.valueOf(r.getTarea().getId()).contains(query) ||
                        r.getVoluntario().getNombre().toLowerCase().contains(query) ||
                        r.getFecha().toString().contains(query))
                .collect(Collectors.toList());

        tblHistorial.setItems(FXCollections.observableArrayList(filtrada));
    }

    @FXML
    private void filtrarPorEstado() {
        String seleccion = cmbFiltroEstado.getValue();
        if (seleccion.equals("Todos")) {
            tblHistorial.setItems(listaRealizaciones);
        } else {
            boolean estado = seleccion.equals("Completadas");
            List<Realiza> filtrada = RealizaDAO.findByCompletada(estado);
            tblHistorial.setItems(FXCollections.observableArrayList(filtrada));
        }
    }

    @FXML
    private void eliminarRegistro() {
        Realiza seleccionada = tblHistorial.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Eliminar este registro de historial?", ButtonType.YES, ButtonType.NO);
            if (confirm.showAndWait().get() == ButtonType.YES) {
                if (RealizaDAO.deleteRealiza(seleccionada.getVoluntario().getId(), seleccionada.getTarea().getId(), seleccionada.getFecha())) {
                    listaRealizaciones.remove(seleccionada);
                }
            }
        }
    }

    @FXML private void abrirAnimales(ActionEvent e) { Utils.irA(e, "AnimalesMain.fxml", "Animales"); }
    @FXML private void abrirVoluntarios(ActionEvent e) { Utils.irA(e, "VoluntariosMain.fxml", "Voluntarios"); }
    @FXML private void abrirTareas(ActionEvent e) { Utils.irA(e, "TareasMain.fxml", "Tareas"); }
    @FXML private void abrirAdoptantes(ActionEvent e) { Utils.irA(e, "AdoptantesMain.fxml", "Adoptantes"); }
    @FXML private void abrirHistorial(ActionEvent e) { Utils.irA(e, "HistorialTareas.fxml", "Historial"); }
    @FXML private void abrirMenuPrincipal(ActionEvent e) { Utils.irA(e, "MenuPrincipal.fxml", "Inicio"); }
    @FXML private void btnSalir(ActionEvent e) { Utils.cerrar(e); }

    @FXML
    private void btnAsignarTarea() throws java.io.IOException {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/es/iesfranciscodelosrios/petadopt/AsignarTarea.fxml"));
        javafx.scene.Scene scene = new javafx.scene.Scene(loader.load());
        javafx.stage.Stage stage = new javafx.stage.Stage();
        stage.setTitle("Asignar Nueva Tarea");
        stage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

        cargarDatos();
    }
}