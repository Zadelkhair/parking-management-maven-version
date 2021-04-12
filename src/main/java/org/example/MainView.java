package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public abstract class MainView {

    public abstract VBox getNavVbox();

    public abstract BorderPane getBp();

    public abstract ButtonBase getBtnHome();

    public void navigateTo(String viewUrl){

        try {
            Parent root = FXMLLoader.load(getClass().getResource(viewUrl));
            getBp().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void navigateTo(String viewUrl, ButtonBase btnToSelect){

        for (Node node : getNavVbox().getChildren()) {

            if(!(node instanceof ButtonBase))
                continue;

            ButtonBase btn = (ButtonBase) node;

            if(btn.getStyleClass().contains("btn-nav-style-selected"))
                btn.getStyleClass().remove("btn-nav-style-selected");

        }

        btnToSelect.getStyleClass().add("btn-nav-style-selected");

        btnToSelect.getStyleClass().contains("");
        navigateTo(viewUrl);
    }

}
