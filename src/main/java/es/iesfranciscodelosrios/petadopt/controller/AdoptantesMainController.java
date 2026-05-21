package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.AdoptanteDAO;
import es.iesfranciscodelosrios.petadopt.model.Adoptante;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdoptantesMainController {

    @FXML private TableView<Adoptante> tvAdoptantes;
    @FXML private TableColumn<Adoptante, Number> colId;
    @FXML private TableColumn<Adoptante, String> colDni;
    @FXML private TableColumn<Adoptante, String> colNombre;
    @FXML private TableColumn<Adoptante, String> colTelefono;
    @FXML private TableColumn<Adoptante, String> colEmail;
    @FXML private TableColumn<Adoptante, Number> colNumAnimales;

    private ObservableList<Adoptante> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        colDni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        colNombre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        colNumAnimales.setCellValueFactory(cellData -> {
            int idAdoptante = cellData.getValue().getId();
            int numAdoptados = AdoptanteDAO.countAnimalesAdoptados(idAdoptante);
            return new SimpleIntegerProperty(numAdoptados);
        });

        cargarAdoptantes();
    }

    private void cargarAdoptantes() {
        masterData.clear();
        List<Adoptante> lista = AdoptanteDAO.findAllAdoptantes();
        if (lista != null) {
            masterData.addAll(lista);
            tvAdoptantes.setItems(masterData);
        }
    }

    @FXML
    private void btnAddAdoptante() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/iesfranciscodelosrios/petadopt/AddAdoptante.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nuevo Adoptante");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.showAndWait();

            cargarAdoptantes();
        } catch (IOException e) {
            System.err.println("Error al cargar AddAdoptante.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void btnEditar() {
        Adoptante seleccionado = tvAdoptantes.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/iesfranciscodelosrios/petadopt/UpdateAdoptante.fxml"));
                Parent root = loader.load();

                UpdateAdoptanteController controladorEdicion = loader.getController();
                controladorEdicion.setAdoptante(seleccionado);

                Stage stage = new Stage();
                stage.setTitle("Modificar Adoptante");
                stage.setScene(new Scene(root));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setResizable(false);
                stage.showAndWait();

                cargarAdoptantes();

            } catch (IOException e) {
                System.err.println("Error al cargar UpdateAdoptante.fxml");
                e.printStackTrace();
            }
        } else {
            mostrarAlertaSeleccion();
        }
    }

    @FXML
    private void btnEliminar() {
        Adoptante seleccionado = tvAdoptantes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            if (AdoptanteDAO.deleteAdoptante(seleccionado.getId())) {
                cargarAdoptantes();
            }
        } else {
            mostrarAlertaSeleccion();
        }
    }

    @FXML
    private void btnDetallatesAdopcion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/iesfranciscodelosrios/petadopt/AdopcionesDetalla.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Registro Detallado de Adopciones");
            stage.setScene(new Scene(root));



            stage.setResizable(true);
            stage.show();

        } catch (IOException e) {
            System.out.println(e);
        }
    }
    private void mostrarAlertaSeleccion() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atención");
        alert.setHeaderText(null);
        alert.setContentText("Por favor, selecciona un adoptante de la tabla primero.");
        alert.showAndWait();
    }

    @FXML private void abrirAnimales(ActionEvent e) { Utils.irA(e, "AnimalesMain.fxml", "Animales"); }
    @FXML private void abrirVoluntarios(ActionEvent e) { Utils.irA(e, "VoluntariosMain.fxml", "Voluntarios"); }
    @FXML private void abrirMenuPrincipal(ActionEvent e) { Utils.irA(e, "MenuPrincipal.fxml", "Inicio"); }

}