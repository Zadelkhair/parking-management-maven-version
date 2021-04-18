package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.MouseEvent;
import org.example.App;

public class SettingViewController {

    @FXML
    private Button btn_parking;

    @FXML
    private Button btn_users;

    @FXML
    private Button btn_sortie_etree;

    @FXML
    private Button btn_place;

    @FXML
    private Button btn_type_place;

    @FXML
    private Button btn_type_member;

    @FXML
    private Button btn_cameras;

    @FXML
    private Button btn_guichets;

    @FXML
    void onClickSettingsButton(MouseEvent event) {
        Object node = event.getSource();

        if(!(node instanceof ButtonBase))
            return;

        ButtonBase btn = (ButtonBase) node;

        switch (btn.getText()){
            case "Cameras":
                App.viewController.navigateTo("primary.fxml");
                return;
        }
    }

}
