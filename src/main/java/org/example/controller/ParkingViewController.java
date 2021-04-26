package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.Member;
import org.example.model.Parking;

import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ParkingViewController implements Initializable {

    @FXML
    private TextField txt_nompark;

    @FXML
    private TextField txt_surf;

    @FXML
    private TextField txt_nbr_place;

    @FXML
    private TableView<Parking> tablev_parking;

    private ArrayList<Parking> parkings;
    private Parking selectedParking;

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        String nom=txt_nompark.getText();
        String nbrplace= txt_nbr_place.getText();
        String surface=txt_surf.getText();
        if(selectedParking!=null){
            //update
            selectedParking.setName(nom);
            selectedParking.setNombre_total(Integer.valueOf(nbrplace));
            selectedParking.setSurface(Float.valueOf(surface));

            if(selectedParking.update()){
                int i = parkings.indexOf(selectedParking);
                if(i>=0){
                    parkings.set(i,selectedParking);
                    selectedParking = null;
                }
                loadTableviewparkingData();
                clearInputs();
            }
        }
        else{
            if(nom.isEmpty()||nbrplace.isEmpty()||surface.isEmpty()){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Remplir tous les champs");
                alert.showAndWait();
                return;
            }
            else {

                //create
                Parking parking = new Parking();
                parking.setId(-1);
                parking.setName(txt_nompark.getText());
                parking.setNombre_total(Integer.valueOf(nbrplace));
                parking.setSurface(Float.parseFloat(surface));

                if (parking.create()) {
                    parkings.add(parking);
                    loadTableviewparkingData();
                    clearInputs();
                }
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        int action = JOptionPane.showConfirmDialog(null, "Confirmer votre suppession?");

        if (action == 0) {
            if (selectedParking != null) {


                if (selectedParking.delete()) {
                    parkings.remove(selectedParking);
                    loadTableviewparkingData();
                    selectedParking = null;
                    this.clearInputs();
                }

            }
            else {JOptionPane.showMessageDialog(null,"Selectionne une element que vous vouler supprimer");}

        }


    }

    @FXML
    void onMouseClickTableView(MouseEvent event) {
        Object obj = tablev_parking.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && obj != null) {
            if (parkings.indexOf(obj) >= 0) {
                selectedParking = (Parking) obj;
                txt_nompark.setText(selectedParking.getName());
                txt_surf.setText(selectedParking.getSurface() + "");
                txt_nbr_place.setText(selectedParking.getNombre_total() + "");
            }
        }
    }

    public void loadParkingData() {
        parkings = new ArrayList<>();

        List<Map<String, Object>> all = (new Parking()).getAll(true);

        for (Map<String, Object> row : all) {
            Parking p = new Parking();
            p.readRow(row);
            parkings.add(p);
        }
    }

    public void loadTableviewparkingData() {
        tablev_parking.getColumns().get(0).setVisible(false);
        tablev_parking.getColumns().get(0).setVisible(true);
        ObservableList<Parking> parkingObservableList = FXCollections.observableList(parkings);
        tablev_parking.setItems(parkingObservableList);
    }

    public void loadTableviewParkingColumns() {
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_parking.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Parking()).toRow().entrySet()) {
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_parking.getColumns().add(column);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadParkingData();
        loadTableviewParkingColumns();
        loadTableviewparkingData();
    }

    private void clearInputs() {
        txt_surf.clear();
        txt_nompark.clear();
        txt_nbr_place.clear();
    }
}
