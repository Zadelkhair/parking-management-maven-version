package org.example.controller;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.example.App;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

public class ReservationStep2ViewController implements IReceiveData, Initializable {

    @FXML
    private Label lbl_place;

    @FXML
    private FlowPane flowpanel;

    private int voiture_id = -1;

    public int getVoiture_id() {
        return voiture_id;
    }

    public void setVoiture_id(int voiture_id) {
        this.voiture_id = voiture_id;
    }


    @Override
    public void setData(Map<String, Object> data) {
        if (data.containsKey("voiture_id")) {
            this.setVoiture_id((Integer) data.get("voiture_id"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            for (int i = 0; i < 20; i++) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("position_card.fxml"));

                Parent root = fxmlLoader.load();
                root.setUserData(fxmlLoader);

                PositionCardViewController positionCardViewController = (PositionCardViewController) fxmlLoader.getController();
                positionCardViewController.setNum(i+1);
                positionCardViewController.setDisponible((new Random()).nextBoolean());

                root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        Label label = (Label) mouseEvent.getTarget();
                        if(label!=null){
                            System.out.println(label.getText());
                        }
                    }
                });

                flowpanel.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void onClickConfermer(MouseEvent mouseEvent) {

    }

}
