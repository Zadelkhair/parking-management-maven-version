package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.Roles;
import org.example.model.TypePlace;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class RolesViewController implements Initializable {
    @FXML
    private TextField txt_role;
    @FXML
    private TableView<Roles> tablev_roles;



    private ArrayList<Roles> roles;
    private Roles selectedRoles;

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        if(selectedRoles!=null){
            //update
            selectedRoles.setName(txt_role.getText());
            if(selectedRoles.update()){
                int i = roles.indexOf(selectedRoles);
                if(i>=0){
                    roles.set(i,selectedRoles);
                    selectedRoles = null;
                }
                selectedRoles = null;
                loadTableviewRoleData();
                clearInputs();
            }
        }
        else{
            if(txt_role.getText().isEmpty()){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("remplir le textfield  ");
                alert.showAndWait();
                return;
            }
            else {
                //create
                Roles role = new Roles();
                role.setName(txt_role.getText());
                if (role.create()) {
                    roles.add(role);

                    loadTableviewRoleData();
                    clearInputs();
                }
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        int action = JOptionPane.showConfirmDialog(null, "Confirmer votre suppession?");
        if (action == 0) {
            if(selectedRoles!=null){
                if(selectedRoles.delete()){
                    roles.remove(selectedRoles);
                    loadTableviewRoleData();
                    selectedRoles = null;
                    this.clearInputs();
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Selectionne une element que vous vouler supprimer");}
        }
    }

    @FXML
    void onMouseClickTableView(MouseEvent event) {
        Object obj = tablev_roles.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && obj != null) {
            if(roles.indexOf(obj) >= 0){
                selectedRoles = (Roles) obj;
                txt_role.setText(selectedRoles.getName());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableviewRoleColumns();
        loadRolesData();
        loadTableviewRoleData();
    }

    public void loadRolesData(){
        roles = new ArrayList<>();

        List<Map<String,Object>> all = (new Roles()).getAll(true);

        for (Map<String,Object> row:all) {
            Roles rl = new Roles();
            rl.readRow(row);
            roles.add(rl);
        }
    }

    public void loadTableviewRoleData(){
        tablev_roles.getColumns().get(0).setVisible(false);
        tablev_roles.getColumns().get(0).setVisible(true);
        ObservableList<Roles> roleObservableList = FXCollections.observableList(roles);
        tablev_roles.setItems(roleObservableList);
    }

    public void loadTableviewRoleColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_roles.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Roles()).toRow().entrySet()) {
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_roles.getColumns().add(column);
        }
    }

    private void clearInputs() {
        txt_role.clear();
    }
}
