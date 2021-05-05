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
import org.example.model.TypePlace;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class TypePlaceViewController implements Initializable {

    @FXML
    private TextField txt_typlace;
    @FXML
    private TableView<TypePlace> tablev_typlace;

    private ArrayList<TypePlace> typePlaces;
    private TypePlace selectedTypePlace;

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        if(selectedTypePlace!=null){
            //update
            selectedTypePlace.setLibelle(txt_typlace.getText());
            if(selectedTypePlace.update()){
                int i = typePlaces.indexOf(selectedTypePlace);
                if(i>=0){
                    typePlaces.set(i,selectedTypePlace);
                    selectedTypePlace = null;
                }
                selectedTypePlace = null;
                loadTableviewTypePlaceData();
                clearInputs();
            }
        }
        else{
            if(txt_typlace.getText().isEmpty()){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("remplir le textfield  ");
                alert.showAndWait();
                return;
            }
            else {
                //create
                TypePlace typePlace = new TypePlace();
                typePlace.setId(-1);
                typePlace.setLibelle(txt_typlace.getText());
                if (typePlace.create()) {
                    typePlaces.add(typePlace);
                    loadTableviewTypePlaceData();
                    clearInputs();
                }
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        int action = JOptionPane.showConfirmDialog(null, "Confirmer votre suppession?");
        if (action == 0) {
        if(selectedTypePlace!=null){
            if(selectedTypePlace.delete()){
                typePlaces.remove(selectedTypePlace);
                loadTableviewTypePlaceData();
                selectedTypePlace = null;
                this.clearInputs();
            }
        }
        else
        {JOptionPane.showMessageDialog(null,"Selectionne une element que vous vouler supprimer");}
        }
    }
    @FXML
    void onClickAnnuler(ActionEvent event) {
        selectedTypePlace = null;
        this.clearInputs();
    }
    @FXML
    void onMouseClickTableView(MouseEvent event) {
        Object obj = tablev_typlace.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && obj != null) {
            if(typePlaces.indexOf(obj) >= 0){
                selectedTypePlace = (TypePlace)obj;
                txt_typlace.setText(selectedTypePlace.getLibelle());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableviewTypePlaceColumns();
        loadTypePlaceData();
        loadTableviewTypePlaceData();
    }

    public void loadTypePlaceData(){
        typePlaces = new ArrayList<>();

        List<Map<String,Object>> all = (new TypePlace()).getAll(true);

        for (Map<String,Object> row:all) {
            TypePlace tp = new TypePlace();
            tp.readRow(row);
            typePlaces.add(tp);
        }
    }

    public void loadTableviewTypePlaceData(){
        tablev_typlace.getColumns().get(0).setVisible(false);
        tablev_typlace.getColumns().get(0).setVisible(true);
        ObservableList<TypePlace> typePlaceObservableList = FXCollections.observableList(typePlaces);
        tablev_typlace.setItems(typePlaceObservableList);
    }

    public void loadTableviewTypePlaceColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_typlace.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new TypePlace()).toRow().entrySet()) {
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_typlace.getColumns().add(column);
        }
    }

    private void clearInputs() {
        txt_typlace.clear();
    }
}
