package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.*;

import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PlaceViewController implements Initializable {

    @FXML
    private ComboBox<CmbTypePlace> cmb_type_place;

    @FXML
    private ComboBox<CmbParking> cmb_type_parking;
    @FXML
    private TableView<Place> tablev_place;
    private ArrayList<CmbTypePlace> typePlaces;
    private ArrayList<CmbParking> parkings;
    private ArrayList<Place> places;
    private Place selectedPlace;

    @FXML
    void onClickSauvgarder(ActionEvent event){
        if(selectedPlace!=null){
            //update
            CmbTypePlace selectedTypePlace = cmb_type_place.getSelectionModel().getSelectedItem();

            if(selectedTypePlace==null)
                return;
            selectedPlace.setId_TP(selectedTypePlace.getId());
            CmbParking selectedParking = cmb_type_parking.getSelectionModel().getSelectedItem();
            if(selectedParking==null)
                return;
            selectedPlace.setId_parking(selectedParking.getId());
            if(selectedPlace.update()){
                int i = places.indexOf(selectedPlace);
                if(i>=0){
                    places.set(i,selectedPlace);
                    selectedPlace = null;
                }
                loadTableviewPlacesData();
                clearInputs();
            }
        }
        else{
            if(cmb_type_parking.getSelectionModel().isEmpty()||cmb_type_place.getSelectionModel().isEmpty())
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("entrer tous les champs ");
                alert.showAndWait();
                return;
            }
            else {
                //create
                Place place = new Place();
                place.setId(-1);

                CmbTypePlace selectedPlace = cmb_type_place.getSelectionModel().getSelectedItem();
                place.setId_TP(selectedPlace.getId());

                CmbParking selectedParking = cmb_type_parking.getSelectionModel().getSelectedItem();
                place.setId_parking(selectedParking.getId());

                if (place.create()) {
                    places.add(place);
                    loadTableviewPlacesData();
                    clearInputs();
                }
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        int action = JOptionPane.showConfirmDialog(null, "Confirmer votre suppession?");

        if (action == 0) {
        if(selectedPlace!=null){
            if(selectedPlace.delete()){
                places.remove(selectedPlace);
                loadTableviewPlacesData();
                selectedPlace = null;
                this.clearInputs();
            }

        }
        else {JOptionPane.showMessageDialog(null,"Selectionne une element que vous vouler supprimer");}

        }
    }

    @FXML
    void onMouseClickTableView(MouseEvent event) {
        Object obj = tablev_place.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && obj != null) {
            if(places.indexOf(obj) >= 0){
                selectedPlace = (Place)obj;

                CmbTypePlace typePlace = new CmbTypePlace();
                typePlace.setId(selectedPlace.getId_TP());
                if(typePlace.read()){
                    cmb_type_place.getSelectionModel().select(typePlace);
                }

                CmbParking parking = new CmbParking();
                parking.setId(selectedPlace.getId_parking());
                if(parking.read()){
                    cmb_type_parking.getSelectionModel().select(parking);
                }

            }
        }
    }

    public void loadTypePlacesData(){
        typePlaces = new ArrayList<>();

        List<Map<String,Object>> all = (new CmbTypePlace()).getAll(true);

        for (Map<String,Object> row:all) {
            CmbTypePlace tp = new CmbTypePlace();
            tp.readRow(row);
            typePlaces.add(tp);
        }
    }

    public void loadParkingsData(){
        parkings = new ArrayList<>();
        List<Map<String,Object>> all = (new CmbParking()).getAll(true);
        for (Map<String,Object> row:all) {
            CmbParking p = new CmbParking();
            p.readRow(row);
            parkings.add(p);
        }
    }

    public void loadPlacesData(){
        places = new ArrayList<>();

        List<Map<String,Object>> all = (new Place()).getAll(true);

        for (Map<String,Object> row:all) {
            Place p = new Place();
            p.readRow(row);
            places.add(p);
        }
    }

    public void loadComboboxTypePlaceData(){
        //load cmb type members
        ObservableList<CmbTypePlace> typePlaceObservableList = FXCollections.observableList(typePlaces);
        cmb_type_place.setItems(typePlaceObservableList);
    }

    public void loadComboboxParkingData(){
        //load cmb parkings
        ObservableList<CmbParking> typeParkingObservableList = FXCollections.observableList(parkings);
        cmb_type_parking.setItems(typeParkingObservableList);
    }

    public void loadTableviewPlacesData(){
        tablev_place.getColumns().get(0).setVisible(false);
        tablev_place.getColumns().get(0).setVisible(true);
        ObservableList<Place> placeObservableList = FXCollections.observableList(places);
        tablev_place.setItems(placeObservableList);
    }

    public void loadTableviewMembersColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_place.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Place()).toRow().entrySet()) {

            if(entry.getKey() == "id_TP"){

                column = new TableColumn("type place");
                column.setCellValueFactory(new PropertyValueFactory<>("typePlaceStr"));
                tablev_place.getColumns().add(column);

                continue;
            }

            if(entry.getKey() == "id_parking"){

                column = new TableColumn("parking");
                column.setCellValueFactory(new PropertyValueFactory<>("parkingStr"));
                tablev_place.getColumns().add(column);

                continue;
            }

            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_place.getColumns().add(column);
        }
    }

    private void clearInputs() {
        cmb_type_parking.getSelectionModel().select(null);
        cmb_type_place.getSelectionModel().select(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableviewMembersColumns();
        loadParkingsData();
        loadTypePlacesData();
        loadPlacesData();
        loadComboboxParkingData();
        loadComboboxTypePlaceData();
        loadTableviewPlacesData();
    }

    class CmbTypePlace extends TypePlace {
        @Override
        public String toString() {
            return this.getLibelle();
        }
    }

    class CmbParking extends Parking {
        @Override
        public String toString() {
            return this.getName();
        }
    }

}
