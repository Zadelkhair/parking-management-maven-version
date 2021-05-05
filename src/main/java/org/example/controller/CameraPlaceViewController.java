package org.example.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import org.example.model.Camera;
import org.example.model.CameraP;
import org.example.model.Member;
import org.example.model.Place;

public class CameraPlaceViewController implements Initializable {

    @FXML
    private TextField txt_libelle;

    @FXML
    private TextField txt_url;

    @FXML
    private ComboBox<CmbPlace> cmb_place;

    @FXML
    private TableView<CameraP> tablev_cameras;
    private ArrayList<CmbPlace> places;
    private ArrayList<CameraP> camerasP;
    private CameraP selectedCameraP;

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        if(selectedCameraP!=null){
            //update
            String libelle = txt_libelle.getText();
            String url = txt_url.getText();

            CmbPlace selectedPlace = cmb_place.getSelectionModel().getSelectedItem();
            if(selectedPlace==null)
                return;

            selectedCameraP.setId_place(selectedPlace.getId());

            if(selectedCameraP.update(libelle,url)){
                int i = places.indexOf(selectedCameraP);
                if(i>=0){
                    places.set(i,selectedPlace);
                    selectedPlace = null;
                }
                loadTableviewCamerasData();
                clearInputs();
            }
        }
        else{
            if(txt_url.getText().isEmpty()||txt_libelle.getText().isEmpty()||cmb_place.getSelectionModel().isEmpty())
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("entrer tous les champs ");
                alert.showAndWait();
                return;
            }
            else {
                //create
                CameraP cameraP = new CameraP();
                String libelle = txt_libelle.getText();
                String url = txt_url.getText();

                CmbPlace selectedPlace = cmb_place.getSelectionModel().getSelectedItem();
                if(selectedPlace==null)
                    return;

                cameraP.setId_place(selectedPlace.getId());

                if (cameraP.create(libelle,url)) {
                    camerasP.add(cameraP);
                    loadTableviewCamerasData();
                    clearInputs();
                }
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        int action = JOptionPane.showConfirmDialog(null, "Confirmer votre suppession?");

        if (action == 0) {
            if(selectedCameraP!=null){
                if(selectedCameraP.delete()){
                    camerasP.remove(selectedCameraP);
                    loadTableviewCamerasData();
                    selectedCameraP = null;
                    this.clearInputs();
                }

            }
            else {JOptionPane.showMessageDialog(null,"Selectionne une element que vous vouler supprimer");}

        }
    }

    @FXML
    void onMouseClickTableView(MouseEvent event) {
        Object obj = tablev_cameras.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && obj != null) {
            if (camerasP.indexOf(obj) >= 0) {
                selectedCameraP = (CameraP) obj;
                txt_libelle.setText(selectedCameraP.getEquipementStr());
                txt_url.setText(selectedCameraP.getCameraUrl());

                CmbPlace place = new CmbPlace();
                place.setId(selectedCameraP.getId_place());
                if (place.read()) {
                    cmb_place.getSelectionModel().select(place);
                }

            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //dispCam("Lignano", "http://192.168.1.102:8080/video/mjpeg");
        loadTableviewCameraColumns();
        loadPlaceData();
        loadComboboxPlaceData();
        loadCamerasData();
        loadTableviewCamerasData();
    }

    public void loadPlaceData() {
        places = new ArrayList<>();

        List<Map<String, Object>> all = (new CmbPlace()).getAll(true);

        for (Map<String, Object> row : all) {
            CmbPlace p = new CmbPlace();
            p.readRow(row);
            places.add(p);
        }
    }

    public void loadCamerasData() {
        camerasP = new ArrayList<>();

        List<Map<String, Object>> all = (new CameraP()).getAll(true);

        for (Map<String, Object> row : all) {
            CameraP c = new CameraP();
            c.readRow(row);
            camerasP.add(c);
        }
    }

    public void loadTableviewCamerasData() {
        tablev_cameras.getColumns().get(0).setVisible(false);
        tablev_cameras.getColumns().get(0).setVisible(true);
        ObservableList<CameraP> camperaPObservableList = FXCollections.observableList(camerasP);
        tablev_cameras.setItems(camperaPObservableList);
    }

    public void loadComboboxPlaceData() {
        //load cmb type members
        ObservableList<CmbPlace> placeObservableList = FXCollections.observableList(places);
        cmb_place.setItems(placeObservableList);
    }

    public void loadTableviewCameraColumns() {

        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_cameras.getColumns().add(column);

        //adding custom libelle to camera table view
        //I create a function in CameraP model called getEquipementStr
        //this column will get it value from this function (CameraP -> getEquipementStr())
        column = new TableColumn("equipement");
        column.setCellValueFactory(new PropertyValueFactory<>("equipementStr"));
        tablev_cameras.getColumns().add(column);

        for (Map.Entry<String, Object> entry : (new CameraP()).toRow().entrySet()) {

            if(entry.getKey() == "id_place"){

                column = new TableColumn("place");
                column.setCellValueFactory(new PropertyValueFactory<>("placeStr"));
                tablev_cameras.getColumns().add(column);

                continue;
            }

            if(entry.getKey() == "id_camera"){
                continue;
            }

            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_cameras.getColumns().add(column);
        }

        //adding custom url to camera table view
        //I create a function in CameraP model called getCameraUrl
        //this column will get it value from this function (CameraP -> getCameraUrl())
        column = new TableColumn("url");
        column.setCellValueFactory(new PropertyValueFactory<>("cameraUrl"));
        tablev_cameras.getColumns().add(column);

    }

    private void clearInputs() {
        txt_libelle.clear();
        txt_url.clear();
        cmb_place.getSelectionModel().clearSelection();
    }

    static {
        Webcam.setDriver(new IpCamDriver());
    }

    @FXML
    public void onClickCamera(ActionEvent event) {

        if(this.selectedCameraP == null){
            return;
        }

        dispCam(selectedCameraP.getEquipementStr(), selectedCameraP.getCameraUrl());

    }

    public void dispCam(String name, String url) {

        try {
            if(!IpCamDeviceRegistry.isRegistered(name)){
                IpCamDeviceRegistry.register(name, url, IpCamMode.PUSH);
            }
            int index = 0;
            for (IpCamDevice ip:IpCamDeviceRegistry.getIpCameras()) {
                System.out.println(ip.getName()+" "+name);
                if(ip.getName().equals(name)){
                    break;
                }
                index++;
            }

            if(index>=IpCamDeviceRegistry.getIpCameras().size())
                return;

            WebcamPanel panel = new WebcamPanel(Webcam.getWebcams().get(index));
            panel.setFPSDisplayed(true);
            panel.setDisplayDebugInfo(true);
            panel.setImageSizeDisplayed(true);
            panel.setMirrored(true);

            JFrame window = new JFrame("Test webcam panel");
            window.add(panel);
            window.setResizable(true);
            //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.pack();
            window.setVisible(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public class CmbPlace extends Place {
        @Override
        public String toString() {
            return this.getId() + " [" + getParkingStr() + "]";
        }
    }

}
