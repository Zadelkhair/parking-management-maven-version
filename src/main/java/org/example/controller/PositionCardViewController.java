package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class PositionCardViewController implements Initializable {

    @FXML
    private Label lbl_num_place;

    private boolean disponible;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setDisponible(false);
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {

        if(disponible){
            if (this.lbl_num_place.getStyleClass().contains("place-reserver")) {
                this.lbl_num_place.getStyleClass().remove("place-reserver");
            }
            this.lbl_num_place.getStyleClass().add("place-disponible");
        }
        else{
            if (this.lbl_num_place.getStyleClass().contains("place-disponible")) {
                this.lbl_num_place.getStyleClass().remove("place-disponible");
            }
            this.lbl_num_place.getStyleClass().add("place-reserver");
        }


        this.disponible = disponible;
    }

    public void setNum(int i) {
        this.lbl_num_place.setText(i+"");
    }

    public int getNum() {
        return Integer.parseInt(this.lbl_num_place.getText());
    }

    public void setOnChoseAction(){

    }

    public void onClck(MouseEvent mouseEvent) {
    }
}
