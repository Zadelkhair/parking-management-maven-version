package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class UsersViewController {

    @FXML
    private TextField txt_user;

    @FXML
    private TextField txt_pass;

    @FXML
    private ComboBox<?> cmb_role_user;

    @FXML
    private TableView<?> tablev_users;
    @FXML
    void onClickSauvgarder(ActionEvent event) {

    }

    @FXML
    void onClickSupprimer(ActionEvent event) {

    }
    @FXML
    void onMouseClickTableView(MouseEvent event) {

    }
}
