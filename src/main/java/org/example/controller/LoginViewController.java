package org.example.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import org.example.App;
import org.example.model.User;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginViewController implements Initializable {

    @FXML
    private TextField user;

    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login(MouseEvent mouseEvent) {

        AuthentificationController authentificationController = new AuthentificationController();
        boolean login = authentificationController.login(user.getText(),password.getText());

        System.out.println(login);

        if(login){
            User user = authentificationController.getUser();
            App.auth.setAuthentificatedUser(user);

            Node source = (Node) mouseEvent.getSource();
            Stage stage  = (Stage) source.getScene().getWindow();
            stage.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Usernam or password are incorrect");
            alert.show();
        }

    }

    public void reserv(MouseEvent mouseEvent) {
    }

    public void checkout(MouseEvent mouseEvent) {
    }
}
