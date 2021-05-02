package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.App;
import org.example.model.*;

import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.util.*;

public class SortieEntreeViewController implements Initializable {

    @FXML
    private TextField txt_lib_entre;

    @FXML
    private ComboBox<CmbParking> cmb_park_en;

    @FXML
    private TableView<Entrer> tablev_entree;

    @FXML
    private TextField txt_lib_sortie;

    @FXML
    private ComboBox<CmbParking> cmb_park_so;

    @FXML
    private ComboBox<CmbCamera> cmb_camera_en;

    @FXML
    private ComboBox<CmbCamera> cmb_camera_so;

    @FXML
    private TableView<Sortie> tablev_sortie;
    private List<Entrer> entrers;
    private List<Sortie> sorties;
    private List<CmbParking> parking;
    private Entrer selectedEntrer;
    private Sortie selectedSortie;
    private ArrayList<CmbCamera> cameras;

    @FXML
    void onClickSauvgarderE(ActionEvent event) {
        if (selectedEntrer != null) {
            //update
            selectedEntrer.setLibelle(txt_lib_entre.getText());

            CmbParking selectedparking = cmb_park_en.getSelectionModel().getSelectedItem();
            if (selectedparking == null)
                return;
            selectedEntrer.setId_parking(selectedparking.getId());

            CmbCamera selectedCamera = cmb_camera_en.getSelectionModel().getSelectedItem();
            if (selectedCamera == null)
                return;
            selectedEntrer.setId_camera(selectedCamera.getId());

            if (selectedEntrer.update()) {
                int i = entrers.indexOf(selectedEntrer);
                if (i >= 0) {
                    entrers.set(i, selectedEntrer);
                    selectedEntrer = null;
                }
                loadTableviewEntrerData();
                clearInputsE();
            }
        } else {
            if (txt_lib_entre.getText().isEmpty() || cmb_park_en.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("entrer tous les champs ");
                alert.showAndWait();
                return;
            } else {
                //create
                Entrer entre = new Entrer();
                entre.setId(-1);
                entre.setLibelle(txt_lib_entre.getText());

                CmbParking selectedParking = cmb_park_en.getSelectionModel().getSelectedItem();
                if (selectedParking == null)
                    return;
                entre.setId_parking(selectedParking.getId());

                CmbCamera selectedCamera = cmb_camera_en.getSelectionModel().getSelectedItem();
                if (selectedCamera == null)
                    return;
                entre.setId_camera(selectedCamera.getId());

                if (entre.create()) {
                    entrers.add(entre);
                    loadTableviewEntrerData();
                    clearInputsE();
                }
            }
        }
    }

    @FXML
    void onClickSauvgarderS(ActionEvent event) {
        if (selectedSortie != null) {
            //update
            selectedSortie.setLibelle(txt_lib_sortie.getText());

            CmbParking selectedparking = cmb_park_so.getSelectionModel().getSelectedItem();
            if (selectedparking == null)
                return;
            selectedSortie.setId_parking(selectedparking.getId());

            CmbCamera selectedCamera = cmb_camera_so.getSelectionModel().getSelectedItem();
            if (selectedCamera == null)
                return;
            selectedEntrer.setId_camera(selectedCamera.getId());

            if (selectedSortie.update()) {
                int i = sorties.indexOf(selectedSortie);
                if (i >= 0) {
                    sorties.set(i, selectedSortie);
                    selectedSortie = null;
                }
                loadTableviewSortieData();
                clearInputsS();
            }
        } else {
            if (txt_lib_sortie.getText().isEmpty() || cmb_park_so.getSelectionModel().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("entrer tous les champs ");
                alert.showAndWait();
                return;
            } else {
                //create
                Sortie sortie = new Sortie();
                sortie.setId(-1);
                sortie.setLibelle(txt_lib_sortie.getText());

                CmbParking selectedParking = cmb_park_so.getSelectionModel().getSelectedItem();
                if (selectedParking == null)
                    return;
                sortie.setId_parking(selectedParking.getId());

                CmbCamera selectedCamera = cmb_camera_so.getSelectionModel().getSelectedItem();
                if (selectedCamera == null)
                    return;
                sortie.setId_camera(selectedCamera.getId());

                if (sortie.create()) {
                    sorties.add(sortie);
                    loadTableviewSortieData();
                    clearInputsS();
                }
            }
        }
    }

    //mouse event for enter tableview
    public void onMouseClickTableViewE(MouseEvent mouseEvent) {
        Object obj = tablev_entree.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && obj != null) {
            if (entrers.indexOf(obj) >= 0) {
                selectedEntrer = (Entrer) obj;
                txt_lib_entre.setText(selectedEntrer.getLibelle());

                CmbParking parking = new CmbParking();
                parking.setId(selectedEntrer.getId_parking());
                if (parking.read()) {
                    cmb_park_en.getSelectionModel().select(parking);
                }

                CmbCamera camera = new CmbCamera();
                camera.setId(selectedEntrer.getId_camera());
                if (camera.read()) {
                    cmb_camera_en.getSelectionModel().select(camera);
                }

            }
        }
    }

    //mouse event for 'sortie' tableview
    public void onMouseClickTableViewS(MouseEvent mouseEvent) {
        Object obj = tablev_sortie.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && obj != null) {
            if (sorties.indexOf(obj) >= 0) {
                selectedSortie = (Sortie) obj;
                txt_lib_sortie.setText(selectedSortie.getLibelle());

                CmbParking parking = new CmbParking();
                parking.setId(selectedSortie.getId_parking());
                if (parking.read()) {
                    cmb_park_so.getSelectionModel().select(parking);
                }

                CmbCamera camera = new CmbCamera();
                camera.setId(selectedSortie.getId_camera());
                if (camera.read()) {
                    cmb_camera_so.getSelectionModel().select(camera);
                }

            }
        }
    }

    @FXML
    void onClickAnnulerS(ActionEvent event) {
        selectedSortie = null;
        this.clearInputsS();
    }

    @FXML
    void onClickAnnulerE(ActionEvent event) {
        selectedEntrer = null;
        this.clearInputsE();
    }

    @FXML
    void onClickSupprimerS(ActionEvent event) {
        int action = JOptionPane.showConfirmDialog(null, "Confirmer votre suppession?");

        if (action == 0) {
            if (selectedSortie != null) {
                if (selectedSortie.delete()) {
                    sorties.remove(selectedSortie);
                    loadTableviewSortieData();

                    selectedSortie = null;
                    this.clearInputsS();

                }

            } else {
                JOptionPane.showMessageDialog(null, "Selectionne une element que vous vouler supprimer");
            }

        }
    }

    @FXML
    void onClickSupprimerE(ActionEvent event) {
        int action = JOptionPane.showConfirmDialog(null, "Confirmer votre suppession?");

        if (action == 0) {
            if (selectedEntrer != null) {
                if (selectedEntrer.delete()) {
                    entrers.remove(selectedEntrer);
                    loadTableviewEntrerData();
                    selectedEntrer = null;
                    this.clearInputsE();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selectionne une element que vous vouler supprimer");
            }

        }
    }

    @FXML
    void onClickDetecterE(ActionEvent event) {

        if (selectedEntrer != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("id_camera", selectedEntrer.getId_camera());
            data.put("type_port", "entrer");
            App.viewController.navigateTo("car_detection.fxml", null, data);
        } else {
            JOptionPane.showMessageDialog(null, "Selectionne une entre pour la detection l'entre d'un vehicule");
        }

    }

    @FXML
    void onClickDetecterS(ActionEvent event) {
        if (selectedSortie != null) {
            Map<String, Object> data = new HashMap<>();
            data.put("id_camera", selectedSortie.getId_camera());
            data.put("type_port", "sortie");
            App.viewController.navigateTo("car_detection.fxml", null, data);
        } else {
            JOptionPane.showMessageDialog(null, "Selectionne une sortie pour la detection la sortie d'un vehicule");
        }
    }

    ///load the data from parking table
    public void loadParkingData() {
        parking = new ArrayList<>();
        List<Map<String, Object>> all = (new CmbParking()).getAll(true);
        for (Map<String, Object> row : all) {
            CmbParking tm = new CmbParking();
            tm.readRow(row);
            parking.add(tm);
        }
    }

    //load data from table entrer
    public void loadEntrerData() {
        entrers = new ArrayList<>();

        List<Map<String, Object>> all = (new Entrer()).getAll(true);

        for (Map<String, Object> row : all) {
            Entrer e = new Entrer();
            e.readRow(row);
            entrers.add(e);
        }
    }

    //load data from table sortie
    public void loadSortieData() {
        sorties = new ArrayList<>();
        List<Map<String, Object>> all = (new Sortie()).getAll(true);
        for (Map<String, Object> row : all) {
            Sortie s = new Sortie();
            s.readRow(row);
            sorties.add(s);
        }
    }

    public void loadTableviewSortieData() {

        tablev_sortie.getColumns().get(0).setVisible(false);
        tablev_sortie.getColumns().get(0).setVisible(true);
        ObservableList<Sortie> sortieObservableList = FXCollections.observableList(sorties);
        tablev_sortie.setItems(sortieObservableList);
    }

    public void loadTableviewEntrerData() {
        tablev_entree.getColumns().get(0).setVisible(false);
        tablev_entree.getColumns().get(0).setVisible(true);
        ObservableList<Entrer> entrerObservableList = FXCollections.observableList(entrers);
        tablev_entree.setItems(entrerObservableList);

    }

    public void loadComboboxParkingData() {
        //load cmb type members
        ObservableList<CmbParking> ParkingObservableList = FXCollections.observableList(parking);
        cmb_park_en.setItems(ParkingObservableList);
        cmb_park_so.setItems(ParkingObservableList);

    }

    public void loadTableviewEntrerColumns() {
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_entree.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Entrer()).toRow().entrySet()) {

            if (entry.getKey() == "id_parking") {

                column = new TableColumn("parking");
                column.setCellValueFactory(new PropertyValueFactory<>("ParkingStr"));
                tablev_entree.getColumns().add(column);

                continue;
            }

            if (entry.getKey() == "id_camera") {

                column = new TableColumn("camera");
                column.setCellValueFactory(new PropertyValueFactory<>("CameraStr"));
                tablev_entree.getColumns().add(column);

                continue;
            }

            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_entree.getColumns().add(column);
        }

    }

    public void loadTableviewSortieColumns() {
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_sortie.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Sortie()).toRow().entrySet()) {
            if (entry.getKey() == "id_parking") {
                column = new TableColumn("parking");
                column.setCellValueFactory(new PropertyValueFactory<>("ParkingStr"));
                tablev_sortie.getColumns().add(column);
                continue;
            }
            if (entry.getKey() == "id_camera") {

                column = new TableColumn("camera");
                column.setCellValueFactory(new PropertyValueFactory<>("CameraStr"));
                tablev_sortie.getColumns().add(column);

                continue;
            }
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_sortie.getColumns().add(column);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadCamerasData();
        this.loadComboboxenCameraData();
        this.loadComboboxsoCameraData();
        this.loadSortieData();
        this.loadEntrerData();
        this.loadParkingData();
        this.loadTableviewEntrerColumns();
        this.loadTableviewSortieColumns();
        this.loadTableviewEntrerData();
        this.loadTableviewSortieData();
        this.loadComboboxParkingData();
    }

    class CmbParking extends Parking {
        @Override
        public String toString() {
            return this.getName();
        }
    }

    private void clearInputsE() {
        txt_lib_entre.clear();
        cmb_park_en.getSelectionModel().select(null);
        cmb_camera_en.getSelectionModel().select(null);

    }

    private void clearInputsS() {
        txt_lib_sortie.clear();
        cmb_park_so.getSelectionModel().select(null);
        cmb_camera_so.getSelectionModel().select(null);
    }

    public void loadCamerasData() {
        cameras = new ArrayList<>();

        List<Map<String, Object>> all = (new CmbCamera()).getAll(true);

        for (Map<String, Object> row : all) {
            CmbCamera c = new CmbCamera();
            c.readRow(row);
            cameras.add(c);
        }
    }

    public void loadComboboxenCameraData() {
        //load cmb type members
        ObservableList<CmbCamera> typeMemberObservableList = FXCollections.observableList(cameras);
        cmb_camera_en.setItems(typeMemberObservableList);
    }

    public void loadComboboxsoCameraData() {
        //load cmb type members
        ObservableList<CmbCamera> typeMemberObservableList = FXCollections.observableList(cameras);
        cmb_camera_so.setItems(typeMemberObservableList);
    }


    class CmbCamera extends Camera {
        @Override
        public String toString() {
            return this.getEquipementStr();
        }
    }

}
