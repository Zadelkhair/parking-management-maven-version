package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

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
