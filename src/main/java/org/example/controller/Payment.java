package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class Payment {
    @FXML
    private TextField txt_nomprenom;

    @FXML
    private TextField txt_typem;

    @FXML
    private TextField txt_matricule;

    @FXML
    private TextField txt_prix;


    @FXML
    private TextField txt_num_place;

    @FXML
    private DatePicker dateE;

    @FXML
    private DatePicker dateS;

    @FXML
    private Button Submit;

    @FXML
    private Button Annuler;

    @FXML
    void onClickAnnuler(ActionEvent event) {

    }

    @FXML
    void onClickSubmit(ActionEvent event) {

    }

}
