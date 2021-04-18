package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.example.App;

import java.util.HashMap;
import java.util.Map;

public class ReservationStep1ViewController {


    @FXML
    private ComboBox<?> cmb_member;

    @FXML
    private HBox box_new_user;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    @FXML
    private DatePicker date_d;

    @FXML
    private DatePicker date_f;

    @FXML
    private ComboBox<?> cmb_type_members;

    @FXML
    private ComboBox<?> cmb_voiture;

    @FXML
    private HBox boxNewUser11;

    @FXML
    private TextField txt_matricule;

    @FXML
    private TextField txt_couleur;

    @FXML
    private TextField txt_marque;

    @FXML
    private Button btn_annuler;

    @FXML
    private Button btn_suiv;

    @FXML
    void onClickSuivant(MouseEvent event) {

        Map<String,Object> data = new HashMap<>();
        data.put("voiture_id",1);

        App.viewController.navigateTo("reservation_step2.fxml", null, data);
    }

    @FXML
    void onClickSupprimer(MouseEvent event) {

    }
}
