package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.App;
import org.example.model.*;

import javax.swing.*;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class UsersViewController  implements Initializable {
    @FXML
    private TextField txt_user;

    @FXML
    private TextField txt_pass;

    @FXML
    private ComboBox<CmbRoles> cmb_role_user;

    @FXML
    private TableView<User> tablev_users;
    private List<User> users;
    private List<CmbRoles> roles;
    private User selectedUser;

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        if(selectedUser!=null){
            //update
            selectedUser.setUtilisateur(txt_user.getText());
            selectedUser.setUtilisateur(txt_pass.getText());
            CmbRoles selectedRoles = cmb_role_user.getSelectionModel().getSelectedItem();

            if(selectedRoles==null)
                return;

            selectedUser.setId_role(selectedRoles.getId());

            if(selectedUser.update()){
                int i = users.indexOf(selectedUser);
                if(i>=0){
                    users.set(i,selectedUser);
                    selectedUser = null;
                }
                loadTableviewUserData();
                clearInputs();
            }
        }
        else{
            if(txt_user.getText().isEmpty()||txt_pass.getText().isEmpty()||cmb_role_user.getSelectionModel().isEmpty())
            {
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("entrer tous les champs ");
                alert.showAndWait();
                return;
            }
            else {
                //create
                User user = new User();
                user.setId(-1);
                user.setUtilisateur(txt_user.getText());
                user.setMotdepasse(txt_pass.getText());
                CmbRoles selectedRoles = cmb_role_user.getSelectionModel().getSelectedItem();
                user.setId_role(selectedRoles.getId());

                if (user.create()) {
                    users.add(user);
                    loadTableviewUserData();
                    clearInputs();
                }
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        int action = JOptionPane.showConfirmDialog(null, "Confirmer votre suppession?");

        if (action == 0) {
            if(selectedUser!=null){
                if(selectedUser.delete()){
                    users.remove(selectedUser);
                    loadTableviewUserData();
                    selectedUser = null;
                    this.clearInputs();
                }

            }
            else {JOptionPane.showMessageDialog(null,"Selectionne une element que vous vouler supprimer");}

        }
    }


    public void onMouseClickTableView(MouseEvent mouseEvent) {
        Object obj = tablev_users.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && obj != null) {
            if(users.indexOf(obj) >= 0){
                selectedUser = (User) obj;
                txt_user.setText(selectedUser.getUtilisateur());
                txt_pass.setText(selectedUser.getMotdepasse());

                CmbRoles role = new CmbRoles();
                role.setId(selectedUser.getId_role());
                if(role.read()){
                    cmb_role_user.getSelectionModel().select(role);
                }

            }
        }
    }

    public void loadRolesData(){
        roles = new ArrayList<>();

        List<Map<String,Object>> all = (new CmbRoles()).getAll(true);

        for (Map<String,Object> row:all) {
            CmbRoles tm = new CmbRoles();
            tm.readRow(row);
            roles.add(tm);
        }
    }

    public void loadUsersData(){
        users = new ArrayList<>();

        List<Map<String,Object>> all = (new User()).getAll(true);

        for (Map<String,Object> row:all) {
            User m = new User();
            m.readRow(row);
            users.add(m);
        }
    }

    public void loadTableviewUserData(){
        tablev_users.getColumns().get(0).setVisible(false);
        tablev_users.getColumns().get(0).setVisible(true);
        ObservableList<User> userObservableList = FXCollections.observableList(users);
        tablev_users.setItems(userObservableList);
    }

    public void loadComboboxRolesData(){
        //load cmb type roles
        ObservableList<CmbRoles> rolesObservableList = FXCollections.observableList(roles);
        cmb_role_user.setItems(rolesObservableList);
    }

    public void loadTableviewUserColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_users.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new User()).toRow().entrySet()) {
            if(entry.getKey() == "id_role"){
                column = new TableColumn("Role d'utilisateur");
                column.setCellValueFactory(new PropertyValueFactory<>("RolesStr"));
                tablev_users.getColumns().add(column);
                continue;
            }
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_users.getColumns().add(column);
        }
    }
    private void clearInputs() {
        txt_pass.clear();
        txt_user.clear();
        cmb_role_user.getSelectionModel().clearSelection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadUsersData();
        this.loadRolesData();
        this.loadTableviewUserColumns();
        this.loadTableviewUserData();
        this.loadComboboxRolesData();
    }

    class CmbRoles extends Roles {
        @Override
        public String toString() {
            return this.getName();
        }
    }
}
