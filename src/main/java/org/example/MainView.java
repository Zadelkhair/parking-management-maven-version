package org.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ButtonBase;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.example.controller.IReceiveData;

import java.io.IOException;
import java.util.Map;

public abstract class MainView {

    public abstract VBox getNavVbox();

    public abstract BorderPane getBp();

    public abstract ButtonBase getBtnHome();

    public void navigateTo(String viewUrl){

        navigateTo(viewUrl,null,null);

    }

    public void navigateTo(String viewUrl, ButtonBase btnToSelect){
        navigateTo(viewUrl,btnToSelect,null);


    }

    public void navigateTo(String viewUrl, ButtonBase btnToSelect, Map<String,Object> data){

        try {

            if(btnToSelect!=null){
                for (Node node : getNavVbox().getChildren()) {

                    System.out.println(node instanceof ButtonBase);

                    if(!(node instanceof AnchorPane))
                        continue;

                    AnchorPane anchorPane = (AnchorPane)node;

                    node = anchorPane.getChildren().get(0);

                    if(!(node instanceof ButtonBase))
                        continue;

                    ButtonBase btn = (ButtonBase) node;

                    if(btn.getStyleClass().contains("btn-nav-style-selected"))
                        btn.getStyleClass().remove("btn-nav-style-selected");

                }

                btnToSelect.getStyleClass().add("btn-nav-style-selected");

                btnToSelect.getStyleClass().contains("");
            }

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(viewUrl));
            Parent root = fxmlLoader.load();

            if(data!=null){
                IReceiveData controller = (IReceiveData)fxmlLoader.getController();
                controller.setData(data);
            }

            getBp().setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
