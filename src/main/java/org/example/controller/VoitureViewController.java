package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class VoitureViewController {
    @FXML
    private TextField txt_matricule;

    @FXML
    private TextField txt_Couleur;

    @FXML
    private TextField txt_Marque;

    @FXML
    private ComboBox<?> cmb_type_voiture;

    @FXML
    private Button Supprimer;

    @FXML
    private Button Sauvegarder;

    @FXML
    private TableView<?> tablev_voiture;

    @FXML
    void onClickSauvgarder(ActionEvent event) {

    }

    @FXML
    void onClickSupprimer(ActionEvent event) {

    }

    @FXML
    void onMouseClickTableView(MouseEvent event)
    {

    }
}
