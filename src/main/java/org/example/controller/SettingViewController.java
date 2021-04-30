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
            case "Users":
                App.viewController.navigateTo("users.fxml");
                return;
            case "Sortie/Entree":
                App.viewController.navigateTo("sortie_entree.fxml");
                return;
            case "Type place":
                App.viewController.navigateTo("type_place.fxml");
                return;
            case "Parking":
                App.viewController.navigateTo("parking.fxml");
                return;
            case "Place":
                App.viewController.navigateTo("place.fxml");
                return;
            case "Type member":
                App.viewController.navigateTo("type_member.fxml");
                return;
            case "Cameras":
                App.viewController.navigateTo("camera.fxml");
                return;
            case "Guichets":
                App.viewController.navigateTo("guichet.fxml");
                return;
            case "Roles":
                App.viewController.navigateTo("roles.fxml");
                return;
        }
    }

}
