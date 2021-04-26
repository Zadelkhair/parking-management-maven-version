package org.example.controller;

import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.example.model.Member;
import org.example.model.Model;
import org.example.model.TypeMember;

public class MemberViewController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    @FXML
    private DatePicker date_d;

    @FXML
    private DatePicker date_f;

    @FXML
    private ComboBox<CmbTypeMember> cmb_type_members;

    @FXML
    private TableView<Member> tablev_members;

    private List<Member> members;
    private List<CmbTypeMember> typeMembers;
    private Member selectedMember;

    @FXML
    void onClickSauvgarder(ActionEvent event) {
        if(selectedMember!=null){
            //update
            selectedMember.setName(txt_nom.getText());
            selectedMember.setPrenom(txt_prenom.getText());
            selectedMember.setDebut_abonnement(Date.valueOf(date_d.getValue()));
            selectedMember.setFin_abonnement(Date.valueOf(date_f.getValue()));
            CmbTypeMember selectedTypeMember = cmb_type_members.getSelectionModel().getSelectedItem();

            if(selectedTypeMember==null)
                return;

            selectedMember.setId_member_type(selectedTypeMember.getId());

            if(selectedMember.update()){
                int i = members.indexOf(selectedMember);
                if(i>=0){
                    members.set(i,selectedMember);
                    selectedMember = null;
                }
                loadTableviewMembersData();
                clearInputs();
            }
        }
        else{
            //create
            Member member = new Member();
            member.setId(-1);
            member.setName(txt_nom.getText());
            member.setPrenom(txt_prenom.getText());
            member.setDebut_abonnement(Date.valueOf(date_d.getValue()));
            member.setFin_abonnement(Date.valueOf(date_f.getValue()));
            CmbTypeMember selectedTypeMember = cmb_type_members.getSelectionModel().getSelectedItem();
            member.setId_member_type(selectedTypeMember.getId());

            if(member.create()){
                members.add(member);
                loadTableviewMembersData();
                clearInputs();
            }
        }
    }

    @FXML
    void onClickSupprimer(ActionEvent event) {
        if(selectedMember!=null){
            if(selectedMember.delete()){
                members.remove(selectedMember);
                loadTableviewMembersData();
                selectedMember = null;
                this.clearInputs();
            }
        }
    }

    @FXML
    void initialize() {

        this.loadMembresData();
        this.loadTypeMembresData();
        this.loadTableviewMembersColumns();
        this.loadTableviewMembersData();
        this.loadComboboxTypeMemberData();

    }

    public void loadTypeMembresData(){
        typeMembers = new ArrayList<>();

        List<Map<String,Object>> all = (new CmbTypeMember()).getAll(true);

        for (Map<String,Object> row:all) {
            CmbTypeMember tm = new CmbTypeMember();
            tm.readRow(row);
            typeMembers.add(tm);
        }
    }

    public void loadMembresData(){
        members = new ArrayList<>();

        List<Map<String,Object>> all = (new Member()).getAll(true);

        for (Map<String,Object> row:all) {
            Member m = new Member();
            m.readRow(row);
            members.add(m);
        }
    }

    public void loadTableviewMembersData(){
        tablev_members.getColumns().get(0).setVisible(false);
        tablev_members.getColumns().get(0).setVisible(true);
        ObservableList<Member> memberObservableList = FXCollections.observableList(members);
        tablev_members.setItems(memberObservableList);
    }

    public void loadComboboxTypeMemberData(){
        //load cmb type members
        ObservableList<CmbTypeMember> typeMemberObservableList = FXCollections.observableList(typeMembers);
        cmb_type_members.setItems(typeMemberObservableList);
    }

    public void loadTableviewMembersColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_members.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Member()).toRow().entrySet()) {
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_members.getColumns().add(column);
        }
    }

    public void onMouseClickTableView(MouseEvent mouseEvent) {
        Object obj = tablev_members.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && obj != null) {
            if(members.indexOf(obj) >= 0){
                selectedMember = (Member)obj;
                txt_nom.setText(selectedMember.getName());
                txt_prenom.setText(selectedMember.getPrenom());
                date_d.setValue(selectedMember.getDebut_abonnement().toLocalDate());
                date_f.setValue(selectedMember.getFin_abonnement().toLocalDate());

                CmbTypeMember typeMember = new CmbTypeMember();
                typeMember.setId(selectedMember.getId_member_type());
                if(typeMember.read()){
                    cmb_type_members.getSelectionModel().select(typeMember);
                }

            }
        }
    }

    private void clearInputs() {
        txt_prenom.clear();
        txt_nom.clear();
        cmb_type_members.getSelectionModel().clearSelection();
        date_d.setValue(null);
        date_f.setValue(null);
    }

    class CmbTypeMember extends TypeMember {
        @Override
        public String toString() {
            return this.getLebelle();
        }
    }

}
