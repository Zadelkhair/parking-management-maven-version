package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ReservationStep2ViewController implements IReceiveData , Initializable {

    @FXML
    private Label lbl_place;

    private int voiture_id = -1;

    public int getVoiture_id() {
        return voiture_id;
    }

    public void setVoiture_id(int voiture_id) {
        this.voiture_id = voiture_id;
    }


    @Override
    public void setData(Map<String, Object> data) {
        if(data.containsKey("voiture_id")){
            this.setVoiture_id((Integer) data.get("voiture_id"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lbl_place.setText(String.valueOf(this.getVoiture_id()));
    }
}
