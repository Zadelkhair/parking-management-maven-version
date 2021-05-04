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
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.App;
import org.example.model.Camera;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CarDetectionViewController implements IReceiveData, Initializable {

    private Camera camera;

    @FXML
    private TextField txt_img;

    @FXML
    private CheckBox use_img;

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
        txt_img.setText(System.getProperty("user.dir") + "/scripts/plate_detection/image2.jpg");
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
        camera = new Camera();
        camera.setId(getId_camera());

        if (!camera.read())
            return;

        dispCam(camera.getEquipementStr(), camera.getUrl());
    }

    @FXML
    void onClickConfirmer(ActionEvent event) {
        if (txt_plate.getText().length() > 0) {
            Map<String, Object> data = new HashMap<>();
            data.put("matricule", txt_plate.getText());
            App.viewController.navigateTo("payment.fxml", null, data);
        } else {
            JOptionPane.showMessageDialog(null, "Selectionne une sortie pour la detection la sortie d'un vehicule");
        }
    }

    @FXML
    void onClickDetecter(ActionEvent event) {

        String s = null;
        String img_url = txt_img.getText();
        String out_file = "";
        String out_plate = "";

        txt_script_out.setText("Wait ...");

        img.setImage(new Image("file:///" + img_url));

        try {

            // run the Unix "ps -ef" command
            // using the Runtime exec method:
            Process p;
            if (use_img.isSelected()) {
                System.out.println("py " + System.getProperty("user.dir") + "/scripts/plate_detection/main.py " + img_url + " img");
                p = Runtime.getRuntime().exec("py " + System.getProperty("user.dir") + "/scripts/plate_detection/main.py " + img_url + " img");

            } else {

                if (camera == null)
                    return;

                if (camera.getUrl().length() <= 0)
                    return;

                p = Runtime.getRuntime().exec("py " + System.getProperty("user.dir") + "/scripts/plate_detection/main.py " + camera.getUrl() + " ip");
            }

            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));

            BufferedReader stdError = new BufferedReader(new
                    InputStreamReader(p.getErrorStream()));

            txt_script_out.clear();

            // read the output from the command
            System.out.println("Here is the standard output of the command:\n");
            txt_script_out.appendText("Here is the standard output of the command:\n\n");
            while ((s = stdInput.readLine()) != null) {
                System.out.println(s);
                txt_script_out.appendText(s + "\n");

                if (s.indexOf("out_file") != -1) {
                    out_file = s.split(":")[1];
                    System.out.println("file:///" + System.getProperty("user.dir") + "/scripts/plate_detection/" + out_file);

                    String out_file_path = "file:///" + System.getProperty("user.dir") + "/scripts/plate_detection/" + out_file;
                    File f = new File(out_file_path);
                    if (f.exists() && !f.isDirectory()) {
                        img.setImage(new Image(out_file_path));
                    }
                }

                if (s.indexOf("out_plate") != -1) {
                    out_plate = s.split(":")[1];
                    System.out.println(out_plate);
                    txt_plate.setText(out_plate);
                }

            }

            // read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            txt_script_out.appendText("Here is the standard error of the command (if any):\n\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
                txt_script_out.appendText(s + "\n");
            }

            //System.exit(0);

        } catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            txt_script_out.appendText("exception happened - here's what I know: \n");
            e.printStackTrace();
            //System.exit(-1);
        }
    }

    static {
        Webcam.setDriver(new IpCamDriver());
    }

    public void dispCam(String name, String url) {

        try {
            if (!IpCamDeviceRegistry.isRegistered(name)) {
                IpCamDeviceRegistry.register(name, url, IpCamMode.PUSH);
            }
            int index = 0;
            for (IpCamDevice ip : IpCamDeviceRegistry.getIpCameras()) {
                System.out.println(ip.getName() + " " + name);
                if (ip.getName().equals(name)) {
                    break;
                }
                index++;
            }

            if (index >= IpCamDeviceRegistry.getIpCameras().size())
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
