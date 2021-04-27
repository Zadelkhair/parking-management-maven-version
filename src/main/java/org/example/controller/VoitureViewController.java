package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.model.Member;
import org.example.model.Voiture;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class VoitureViewController implements Initializable {
    @FXML
    private TextField txt_matricule;

    @FXML
    private TextField txt_Couleur;

    @FXML
    private TextField txt_Marque;

    @FXML
    private ComboBox<CmbMember> cmb_type_member;

    @FXML
    private Button Supprimer;

    @FXML
    private Button Sauvegarder;

    @FXML
    private TableView<Voiture> tablev_voiture;
    private ArrayList<Voiture> voitures;
    private ArrayList<CmbMember> members;
    private Voiture selectedVoiture;

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        if(selectedVoiture!=null){
            //update
            selectedVoiture.setColour(txt_Couleur.getText());
            selectedVoiture.setMatricule(txt_matricule.getText());
            selectedVoiture.setLa_marque(txt_Marque.getText());

            CmbMember selectedMember = cmb_type_member.getSelectionModel().getSelectedItem();

            if(selectedMember==null)
                return;

            selectedVoiture.setId_m(selectedMember.getId());

            if(selectedVoiture.update()){
                int i = voitures.indexOf(selectedVoiture);
                if(i>=0){
                    voitures.set(i,selectedVoiture);
                    selectedVoiture = null;
                }
                loadTableviewVoituresData();
                clearInputs();
            }
        }
        else{
            //create
            Voiture voiture = new Voiture();
            voiture.setId(-1);
            voiture.setColour(txt_Couleur.getText());
            voiture.setMatricule(txt_matricule.getText());
            voiture.setLa_marque(txt_Marque.getText());
            CmbMember selectedMember = cmb_type_member.getSelectionModel().getSelectedItem();
            voiture.setId_m(selectedMember.getId());

            if(voiture.create()){
                voitures.add(voiture);
                loadTableviewVoituresData();
                clearInputs();
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        if(selectedVoiture !=null){
            if(selectedVoiture.delete()){
                voitures.remove(selectedVoiture);
                loadTableviewVoituresData();
                selectedVoiture = null;
                this.clearInputs();
            }
        }
    }

    @FXML
    void onMouseClickTableView(MouseEvent event) {
        Object obj = tablev_voiture.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && obj != null) {
            if(voitures.indexOf(obj) >= 0){
                selectedVoiture = (Voiture) obj;
                txt_matricule.setText(selectedVoiture.getMatricule());
                txt_Couleur.setText(selectedVoiture.getColour());
                txt_Marque.setText(selectedVoiture.getLa_marque());

                CmbMember typeMember = new CmbMember();
                typeMember.setId(selectedVoiture.getId_m());
                if(typeMember.read()){
                    cmb_type_member.getSelectionModel().select(typeMember);
                }

            }
        }
    }

    private void clearInputs() {
        txt_Couleur.clear();
        txt_Marque.clear();
        txt_matricule.clear();
        cmb_type_member.getSelectionModel().select(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableviewVoituresColumns();
        loadVoituresData();
        loadTableviewVoituresData();
        loadMembresData();
        loadComboboxTypeMemberData();
    }

    public void loadVoituresData(){
        voitures = new ArrayList<>();

        List<Map<String,Object>> all = (new Voiture()).getAll(true);

        for (Map<String,Object> row:all) {
            Voiture v = new Voiture();
            v.readRow(row);
            voitures.add(v);
        }
    }

    public void loadMembresData(){
        members = new ArrayList<>();

        List<Map<String,Object>> all = (new CmbMember()).getAll(true);

        for (Map<String,Object> row:all) {
            CmbMember m = new CmbMember();
            m.readRow(row);
            members.add(m);
        }
    }

    public void loadComboboxTypeMemberData(){
        //load cmb type members
        ObservableList<CmbMember> memberObservableList = FXCollections.observableList(members);
        cmb_type_member.setItems(memberObservableList);
    }

    public void loadTableviewVoituresData(){
        tablev_voiture.getColumns().get(0).setVisible(false);
        tablev_voiture.getColumns().get(0).setVisible(true);
        ObservableList<Voiture> voitureObservableList = FXCollections.observableList(voitures);
        tablev_voiture.setItems(voitureObservableList);
    }

    public void loadTableviewVoituresColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_voiture.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Voiture()).toRow().entrySet()) {
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_voiture.getColumns().add(column);
        }
    }

    class CmbMember extends Member {
        @Override
        public String toString() {
            return this.getPrenom() + " " + this.getName() ;
        }
    }

}
