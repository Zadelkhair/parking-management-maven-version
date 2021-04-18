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

    @FXML
    void onClickSauvgarder(ActionEvent event) {

    }

    @FXML
    void onClickSupprimer(ActionEvent event) {

    }

    @FXML
    void initialize() {
        members = new ArrayList<>();

        Member m1 = new Member();
        m1.setId(1);
        m1.setName("zad");
        m1.setPrenom("abdelkoddous");
        m1.setDebut_abonnement(new Date(2020,1,1));
        m1.setFin_abonnement(new Date(2020,5,5));
        m1.setId_member_type(1);
        members.add(m1);

        tablev_members.setItems(FXCollections.observableList(members));

        for (Map.Entry<String, Object> entry : (new Member()).toRow().entrySet()) {
            TableColumn column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_members.getColumns().add(column);
        }

        //load cmb type members
        typeMembers = new ArrayList<>();
        CmbTypeMember tm1 = new CmbTypeMember();
        tm1.setId(1);
        tm1.setLebelle("Hot");
        typeMembers.add(tm1);
        cmb_type_members.setItems(FXCollections.observableList(typeMembers));

    }

    public void onMouseClickTableView(MouseEvent mouseEvent) {
        Object obj = tablev_members.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && obj != null) {
            System.out.println(members.indexOf(obj));
            System.out.println(typeMembers.indexOf(cmb_type_members.getValue()));
        }
    }

    class CmbTypeMember extends TypeMember {
        @Override
        public String toString() {
            return this.getLebelle();
        }
    }
}
