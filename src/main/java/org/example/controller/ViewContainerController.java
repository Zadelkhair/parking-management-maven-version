package org.example.controller;

import javafx.scene.control.ButtonBase;
import javafx.scene.input.MouseEvent;
import org.example.App;

public class ViewContainerController {
    public void navBtnClick(MouseEvent mouseEvent) {

        Object node = mouseEvent.getSource();

        if(!(node instanceof ButtonBase))
            return;

        ButtonBase btn = (ButtonBase) node;

        switch (btn.getText()){
            case "Camera":
                App.viewController.navigateTo("cameras.fxml",btn);
                return;
            case "Parking lot":
                App.viewController.navigateTo("parkinLot.fxml",btn);
                return;
            case "Sample3":
                App.viewController.navigateTo("Simple3.fxml",btn);
                return;
            case "Member":
                App.viewController.navigateTo("members.fxml",btn);
                return;
            case "History":
                App.viewController.navigateTo("history.fxml",btn);
                return;
            case "Settings":
                App.viewController.navigateTo("setting.fxml",btn);
                return;
            case "Users":
                App.viewController.navigateTo("_users.fxml",btn);
                return;
            case "Price":
                App.viewController.navigateTo("sample2.fxml",btn);
                return;
        }
    }
}
