package org.example.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.example.model.CameraP;
import org.example.model.Member;
import org.example.model.Place;

public class CameraViewController implements Initializable {

    @FXML
    private TextField txt_libelle;

    @FXML
    private ComboBox<cmbPlace> cmb_place;

    @FXML
    private TableView<CameraP> tablev_cameras;
    private ArrayList<cmbPlace> places;
    private ArrayList<CameraP> camerasP;

    @FXML
    void onClickSauvgarder(ActionEvent event) {

    }

    @FXML
    void onClickSupprimer(ActionEvent event) {

    }

    @FXML
    void onMouseClickTableView(MouseEvent event) {

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

    public void loadPlaceData(){
        places = new ArrayList<>();

        List<Map<String,Object>> all = (new cmbPlace()).getAll(true);

        for (Map<String,Object> row:all) {
            cmbPlace p = new cmbPlace();
            p.readRow(row);
            places.add(p);
        }
    }

    public void loadCamerasData(){
        camerasP = new ArrayList<>();

        List<Map<String,Object>> all = (new CameraP()).getAll(true);

        for (Map<String,Object> row:all) {
            CameraP c = new CameraP();
            c.readRow(row);
            camerasP.add(c);
        }
    }

    public void loadTableviewCamerasData(){
        tablev_cameras.getColumns().get(0).setVisible(false);
        tablev_cameras.getColumns().get(0).setVisible(true);
        ObservableList<CameraP> camperaPObservableList = FXCollections.observableList(camerasP);
        tablev_cameras.setItems(camperaPObservableList);
    }

    public void loadComboboxPlaceData(){
        //load cmb type members
        ObservableList<cmbPlace> placeObservableList = FXCollections.observableList(places);
        cmb_place.setItems(placeObservableList);
    }

    public void loadTableviewCameraColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_cameras.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new CameraP()).toRow().entrySet()) {

//            if(entry.getKey() == "id_member_type"){
//
//                column = new TableColumn("type member");
//                column.setCellValueFactory(new PropertyValueFactory<>("memberTypeStr"));
//                tablev_members.getColumns().add(column);
//
//                continue;
//            }

            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_cameras.getColumns().add(column);
        }
    }

    private void clearInputs() {
        txt_libelle.clear();
        cmb_place.getSelectionModel().clearSelection();
    }

    public void dispCam(String name,String url){
        try {
            IpCamDeviceRegistry.register(name, url, IpCamMode.PUSH);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        WebcamPanel panel = new WebcamPanel(Webcam.getWebcams().get(0));
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
    }

    public class cmbPlace extends Place{
        @Override
        public String toString() {
            return this.getId()+" ["+getParkingStr()+"]";
        }
    }
}
