package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.example.App;
import org.example.model.Member;
import org.example.model.Voiture;

import java.net.URL;
import java.util.*;

public class ReservationStep1ViewController implements Initializable {


    @FXML
    private ComboBox<CmbMember> cmb_member;

    @FXML
    private HBox box_new_user;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    @FXML
    private DatePicker date_d;

    @FXML
    private DatePicker date_f;

    @FXML
    private ComboBox<CmbMember> cmb_type_members;

    @FXML
    private ComboBox<CmbVoiture> cmb_voiture;

    @FXML
    private HBox boxNewUser11;

    @FXML
    private TextField txt_matricule;

    @FXML
    private TextField txt_couleur;

    @FXML
    private TextField txt_marque;

    @FXML
    private Button btn_annuler;

    @FXML
    private Button btn_suiv;

    private ArrayList<CmbVoiture> voitures;
    private ArrayList<CmbMember> members;
    private CmbVoiture selectedVoiture;


    @FXML
    void onClickSuivant(MouseEvent event) {

        CmbVoiture selectedVoiture = cmb_voiture.getSelectionModel().getSelectedItem();

        if(selectedVoiture==null)
            return;

        Map<String,Object> data = new HashMap<>();
        data.put("voiture_id",selectedVoiture.getId());

        App.viewController.navigateTo("reservation_step2.fxml", null, data);
    }
    @FXML
    void onClickAnnuler(ActionEvent event) {

        this.clearInputs();

    }
    private void clearInputs() {
        txt_matricule.clear();
        txt_couleur.clear();
        txt_nom.clear();
        txt_prenom.clear();
        txt_marque.clear();
        date_d.getEditor().clear();
        date_f.getEditor().clear();
        cmb_type_members.getSelectionModel().clearSelection();
        cmb_voiture.getSelectionModel().clearSelection();


    }
    @FXML
    void onClickSupprimer(MouseEvent event) {

    }

    @FXML
    void onActionMember(ActionEvent event) {
        CmbMember cmbMember = cmb_member.getSelectionModel().getSelectedItem();
        loadMembreVoitursData(cmbMember.getId());
        loadComboboxVoitureData();
    }

    private void loadComboboxVoitureData() {
        //load cmb voitures
        ObservableList<CmbVoiture> voituresObservableList = FXCollections.observableList(voitures);
        cmb_voiture.setItems(voituresObservableList);
    }

    public void loadMembreVoitursData(int id_m){
        voitures = new ArrayList<>();

        List<Map<String,Object>> all = (new CmbVoiture()).getAllByMember(id_m);

        for (Map<String,Object> row:all) {
            CmbVoiture v = new CmbVoiture();
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
        cmb_member.setItems(memberObservableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadMembresData();
        loadComboboxTypeMemberData();
    }

    class CmbMember extends Member {
        @Override
        public String toString() {
            return this.getPrenom() + " " + this.getName() ;
        }
    }

    class CmbVoiture extends Voiture {
        @Override
        public String toString() {
            return this.getMatricule() + " (" + this.getMatricule() + ")" ;
        }
    }

}
