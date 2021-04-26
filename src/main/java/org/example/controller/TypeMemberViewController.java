package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.Member;
import org.example.model.TypeMember;

import java.net.URL;
import java.sql.Date;
import java.util.*;

public class TypeMemberViewController implements Initializable {

    @FXML
    private TextField txt_tymembre;

    @FXML
    private TableView<TypeMember> tablev_tymembre;

    private List<TypeMember> typeMembers;

    private TypeMember selectedTypeMemeber;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.loadTypeMembresData();
        loadTableviewTypeMembersColumns();
        loadTableviewTypeMembersData();
    }

    public void loadTypeMembresData(){
        typeMembers = new ArrayList<>();

        List<Map<String,Object>> all = (new TypeMember()).getAll(true);

        for (Map<String,Object> row:all) {
            TypeMember tm = new TypeMember();
            tm.readRow(row);
            typeMembers.add(tm);
        }
    }

    public void loadTableviewTypeMembersData(){
        tablev_tymembre.getColumns().get(0).setVisible(false);
        tablev_tymembre.getColumns().get(0).setVisible(true);
        ObservableList<TypeMember> typeMemberObservableList = FXCollections.observableList(typeMembers);
        tablev_tymembre.setItems(typeMemberObservableList);
    }

    public void loadTableviewTypeMembersColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_tymembre.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new TypeMember()).toRow().entrySet()) {
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_tymembre.getColumns().add(column);
        }
    }

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        if(selectedTypeMemeber!=null){
            //update
            selectedTypeMemeber.setLebelle(txt_tymembre.getText());
            if(selectedTypeMemeber.update()){
                int i = typeMembers.indexOf(selectedTypeMemeber);
                if(i>=0){
                    typeMembers.set(i,selectedTypeMemeber);
                    selectedTypeMemeber = null;
                }
                loadTableviewTypeMembersData();
                clearInputs();
            }
        }
        else{
            //create
            TypeMember typeMember = new TypeMember();
            typeMember.setId(-1);
            typeMember.setLebelle(txt_tymembre.getText());
            if(typeMember.create()){
                typeMembers.add(typeMember);
                loadTableviewTypeMembersData();
                clearInputs();
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        if(selectedTypeMemeber!=null){
            if(selectedTypeMemeber.delete()){
                typeMembers.remove(selectedTypeMemeber);
                loadTableviewTypeMembersData();
                selectedTypeMemeber = null;
                this.clearInputs();
            }
        }
    }

    @FXML
    void onMouseClickTableView(MouseEvent event){
        Object obj = tablev_tymembre.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && obj != null) {
            if(typeMembers.indexOf(obj) >= 0){
                selectedTypeMemeber = (TypeMember)obj;
                txt_tymembre.setText(selectedTypeMemeber.getLebelle());
            }
        }
    }

    public void clearInputs(){
        this.txt_tymembre.clear();
    }

}
