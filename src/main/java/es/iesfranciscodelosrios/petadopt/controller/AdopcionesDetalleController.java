package es.iesfranciscodelosrios.petadopt.controller;

import es.iesfranciscodelosrios.petadopt.dao.AdoptanteDAO;
import es.iesfranciscodelosrios.petadopt.model.AdopcionDetalle;
import es.iesfranciscodelosrios.petadopt.utils.Utils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AdopcionesDetalleController {

    @FXML private TextField txtBuscar;
    @FXML private TableView<AdopcionDetalle> tvAdopciones;
    @FXML private TableColumn<AdopcionDetalle, String> colNombreAdoptante;
    @FXML private TableColumn<AdopcionDetalle, String> colTelefono;
    @FXML private TableColumn<AdopcionDetalle, String> colNombreAnimal;
    @FXML private TableColumn<AdopcionDetalle, String> colRaza;

    private ObservableList<AdopcionDetalle> masterData = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colNombreAdoptante.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreAdoptante()));
        colTelefono.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTelefonoAdoptante()));
        colNombreAnimal.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombreAnimal()));
        colRaza.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRazaAnimal()));

        cargarAdopciones();

        FilteredList<AdopcionDetalle> filteredData = new FilteredList<>(masterData, p -> true);

        txtBuscar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(adopcion -> {
                if (newValue == null || newValue.isEmpty()) return true;
                String lowerCaseFilter = newValue.toLowerCase();
                if (adopcion.getNombreAdoptante().toLowerCase().contains(lowerCaseFilter)) return true;
                else return adopcion.getNombreAnimal().toLowerCase().contains(lowerCaseFilter);
            });
        });

        tvAdopciones.setItems(filteredData);
    }

    private void cargarAdopciones() {
        masterData.clear();
        List<AdopcionDetalle> lista = AdoptanteDAO.findAdopcionesDetalle();
        if (lista != null) {
            masterData.addAll(lista);
        }
    }

    @FXML
    private void btnAddAdopncion() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/es/iesfranciscodelosrios/petadopt/AddAdopcion.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nueva Adopción");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            cargarAdopciones();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void btnEliminarAdopcion() {
        AdopcionDetalle seleccionada = tvAdopciones.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
            confirmacion.setTitle("Romper Adopción");
            confirmacion.setHeaderText("¿Está seguro de eliminar esta adopción?");
            confirmacion.setContentText("El animal " + seleccionada.getNombreAnimal() + " volverá a estar disponible.");

            if (confirmacion.showAndWait().get() == ButtonType.OK) {
                if (AdoptanteDAO.deleteAdopcion(seleccionada.getIdAnimal())) {
                    cargarAdopciones();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Seleccione una fila de la tabla primero.");
            alert.showAndWait();
        }
    }
}