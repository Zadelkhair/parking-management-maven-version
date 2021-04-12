package org.example.controller;

import javafx.scene.control.ButtonBase;
import javafx.stage.Stage;

public interface IViewController {

    void navigateTo(String viewUrl);

    void navigateTo(String viewUrl, ButtonBase btnToSelect);

    void display(String viewfxml,Stage stage);

    void display(String viewfxml, Stage stage, boolean wait);

    void display(String viewfxml, Stage stage, boolean wait, boolean transparent);

    void display(String viewfxml);

    void displayMain(String viewfxml, Stage stage);

    void displayMain(String viewfxml, Stage stage, boolean wait);
}
