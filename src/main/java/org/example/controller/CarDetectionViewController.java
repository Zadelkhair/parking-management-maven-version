package org.example.controller;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.ds.ipcam.IpCamDevice;
import com.github.sarxos.webcam.ds.ipcam.IpCamDeviceRegistry;
import com.github.sarxos.webcam.ds.ipcam.IpCamDriver;
import com.github.sarxos.webcam.ds.ipcam.IpCamMode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import org.example.model.Camera;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class CarDetectionViewController implements IReceiveData , Initializable {

    private int id_camera;
    private String type_port;

    public int getId_camera() {
        return id_camera;
    }

    public void setId_camera(int id_camera) {
        this.id_camera = id_camera;
    }

    public String getType_port() {
        return type_port;
    }

    public void setType_port(String type_port) {
        this.type_port = type_port;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @Override
    public void setData(Map<String, Object> data) {
        if (data.containsKey("id_camera")) {
            this.setId_camera((Integer) data.get("id_camera"));
            return;
        }
        if (data.containsKey("type_port")) {
            this.setType_port((String) data.get("type_port"));
            return;
        }
    }

    @FXML
    private TextArea txt_script_out;

    @FXML
    private Button btn_detecter;

    @FXML
    private Button btn_camera;

    @FXML
    private Button btn_cofirmer;

    @FXML
    private TextField txt_plate;

    @FXML
    private ImageView img;

    @FXML
    void onClickCamera(ActionEvent event) {
        Camera c = new Camera();
        c.setId(getId_camera());

        if(!c.read())
            return;

        dispCam(c.getEquipementStr(), c.getUrl());
    }

    @FXML
    void onClickConfirmer(ActionEvent event) {

    }

    @FXML
    void onClickDetecter(ActionEvent event) {

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
