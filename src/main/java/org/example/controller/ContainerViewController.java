package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBase;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.App;
import org.example.MainView;

import java.net.URL;
import java.util.ResourceBundle;

public class ContainerViewController extends MainView implements Initializable {

    @FXML
    private VBox navVbox;

    @FXML
    private BorderPane bp;

    @FXML
    private ButtonBase btnHome;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        App.viewController.navigateTo("sample.fxml",btnHome);
    }

    @Override
    public VBox getNavVbox() {
        return this.navVbox;
    }

    @Override
    public BorderPane getBp() {
        return this.bp;
    }

    @Override
    public ButtonBase getBtnHome() {
        return this.btnHome ;
    }

    public void navBtnClick(MouseEvent mouseEvent) {

        Object node = mouseEvent.getSource();

        if(!(node instanceof ButtonBase))
            return;

        ButtonBase btn = (ButtonBase) node;

        switch (btn.getText()){
            case "Home":
//                App.viewController.navigateTo("primary.fxml",btn);
                return;
            case "Reservation":
                App.viewController.navigateTo("reservation_step1.fxml",btn);
                return;
            case "Camera":
                App.viewController.navigateTo("camera.fxml",btn);
                return;
            case "Historique":
                App.viewController.navigateTo("historique.fxml",btn);
                return;
            case "Parking lot":
                App.viewController.navigateTo("historique.fxml",btn);
                return;
            case "Membre":
                App.viewController.navigateTo("members.fxml",btn);
                return;
            case "Voiture":
                App.viewController.navigateTo("voiture.fxml",btn);
                return;
            case "Settings":
                App.viewController.navigateTo("settings.fxml",btn);
                return;
        }

    }
}
