package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PositionCardViewController implements Initializable {

    @FXML
    private Label lbl_num_place;

    private boolean reserved = true;
    private boolean selected = false;

    private static List<PositionCardViewController> selected_positions;
    private boolean multiselect;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.setReserved(false);
    }

    public boolean isMultiselect() {
        return multiselect;
    }

    public boolean isReserved() {
        return reserved;
    }

    public void setReserved(boolean reserved) {

        lbl_num_place.setDisable(reserved);

        if(reserved){
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


        this.reserved = reserved;
    }

    public static void deselectAll(){
        for (PositionCardViewController pc:selected_positions) {
            if(pc.isSelected())
                pc.selectANDdeselect();
        }

        selected_positions.clear();
    }

    public void selectANDdeselect(){
        this.selectANDdeselect(false);
    }

    public void selectANDdeselect(boolean multiselect) {

        this.multiselect = multiselect;

        if(!multiselect){
            if(!this.isSelected()){
                if(selected_positions==null)
                    selected_positions=new ArrayList<>();

                for (PositionCardViewController pc:selected_positions) {
                    if(pc.isSelected())
                        pc.selectANDdeselect();
                }

                selected_positions.add(this);
            }
        }


        if(!selected){
            if (!this.lbl_num_place.getStyleClass().contains("place-slected")) {
                this.lbl_num_place.getStyleClass().add("place-slected");
            }
        }
        else{
            if (this.lbl_num_place.getStyleClass().contains("place-slected")) {
                this.lbl_num_place.getStyleClass().remove("place-slected");
            }
        }

        this.selected = !selected;
    }

    private boolean isSelected() {
        return this.selected;
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
