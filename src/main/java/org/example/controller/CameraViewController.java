package org.example.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.Camera;
import org.example.model.CameraP;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class CameraViewController implements Initializable {

    @FXML
    private TextField txt_libelle;

    @FXML
    private Label lbl1;

    @FXML
    private TextField txt_url;

    @FXML
    private Button btn_camera;

    @FXML
    private TableView<Camera> tablev_cameras;
    private ArrayList<Camera> cameras;
    private Camera selectedCamera;

    @FXML
    void onClickCamera(ActionEvent event) {
        if(this.selectedCamera == null){
            return;
        }

        dispCam(selectedCamera.getEquipementStr(), selectedCamera.getUrl());

    }

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        if(selectedCamera!=null){
            //update
            String libelle = txt_libelle.getText();
            String url = txt_url.getText();

            selectedCamera.setUrl(url);

            if(selectedCamera.update(libelle)){
                loadTableviewCamerasData();
                clearInputs();
            }
        }
        else{
            if(txt_url.getText().isEmpty()||txt_libelle.getText().isEmpty())
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("entrer tous les champs ");
                alert.showAndWait();
                return;
            }
            else {
                //create
                Camera camera = new Camera();
                String libelle = txt_libelle.getText();
                String url = txt_url.getText();

                camera.setUrl(url);

                if (camera.create(libelle)) {
                    cameras.add(camera);
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
            if(selectedCamera!=null){
                if(selectedCamera.delete()){
                    cameras.remove(selectedCamera);
                    loadTableviewCamerasData();
                    selectedCamera = null;
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
            if (cameras.indexOf(obj) >= 0) {
                selectedCamera = (Camera) obj;
                txt_libelle.setText(selectedCamera.getEquipementStr());
                txt_url.setText(selectedCamera.getUrl());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCamerasData();
        loadTableviewCameraColumns();
        loadTableviewCamerasData();
    }

    public void loadCamerasData() {
        cameras = new ArrayList<>();

        List<Map<String, Object>> all = (new Camera()).getAll(true);

        for (Map<String, Object> row : all) {
            Camera c = new Camera();
            c.readRow(row);
            cameras.add(c);
        }
    }

    public void loadTableviewCamerasData() {
        tablev_cameras.getColumns().get(0).setVisible(false);
        tablev_cameras.getColumns().get(0).setVisible(true);
        ObservableList<Camera> camperaObservableList = FXCollections.observableList(cameras);
        tablev_cameras.setItems(camperaObservableList);
    }

    public void loadTableviewCameraColumns() {

        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_cameras.getColumns().add(column);

        for (Map.Entry<String, Object> entry : (new Camera()).toRow().entrySet()) {

            if (entry.getKey() == "id_equipement") {

                column = new TableColumn("equipement");
                column.setCellValueFactory(new PropertyValueFactory<>("equipementStr"));
                tablev_cameras.getColumns().add(column);

                continue;
            }

            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_cameras.getColumns().add(column);
        }

    }

    private void clearInputs() {
        txt_libelle.clear();
        txt_url.clear();
    }

    static {
        Webcam.setDriver(new IpCamDriver());
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

}
