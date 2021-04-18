package org.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBase;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.example.App;
import org.example.MainView;

import java.io.IOException;

public class ViewController implements IViewController {

    private MainView mainView;

    public ViewController() {

    }

    @Override
    public void navigateTo(String viewUrl) {

        if (mainView == null)
            return;

        mainView.navigateTo(viewUrl);
    }

    @Override
    public void navigateTo(String viewUrl, ButtonBase btnToSelect) {

        if (mainView == null)
            return;

        mainView.navigateTo(viewUrl, btnToSelect);
    }

    @Override
    public void display(String viewfxml, Stage stage) {
        this.display(viewfxml, stage, false);
    }

    @Override
    public void display(String viewfxml, Stage stage, boolean wait) {
        this.display(viewfxml, stage, wait, false);
    }

    @Override
    public void display(String viewfxml, Stage stage, boolean wait, boolean transparent) {

        //scene
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewfxml));

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Object obj = fxmlLoader.getController();

            if (transparent) {
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
            }

            stage.setScene(scene);

            if (!wait)
                stage.show();
            else
                stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void display(String viewfxml) {
        this.display(viewfxml, new Stage());
    }

    @Override
    public void displayMain(String viewfxml, Stage stage) {
        this.displayMain(viewfxml, stage, false);
    }

    @Override
    public void displayMain(String viewfxml, Stage stage, boolean wait, boolean transparent) {

        //scene
        try {

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewfxml));

            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);

            Object obj = fxmlLoader.getController();
            this.mainView = (MainView) obj;

            if (transparent) {
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);
            }

            stage.setScene(scene);

            if (!wait)
                stage.show();
            else
                stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void displayMain(String viewfxml, Stage stage, boolean wait ){
        this.displayMain(viewfxml, stage, wait,false);
    }
}
