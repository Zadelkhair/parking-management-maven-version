package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.example.model.*;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ResourceBundle;

public class PaymentViewController implements IReceiveData , Initializable {
    @FXML
    private TextField txt_nomprenom;

    @FXML
    private TextField txt_typem;

    @FXML
    private TextField txt_matricule;

    @FXML
    private TextField txt_parking;

    @FXML
    private TextField txt_num_place;

    @FXML
    private DatePicker dateE;

    @FXML
    private TextField txt_timee;

    @FXML
    private DatePicker dateS;

    @FXML
    private TextField txt_times;

    @FXML
    private TextField txt_prix;

    @FXML
    private Button Submit;

    @FXML
    private Button Annuler;

    private Voiture voiture;
    private TypeMember type_membre;
    private Member membre;
    private Reservation reservation;
    private Place place;
    private Parking parking;

    @FXML
    void onClickAnnuler(ActionEvent event) {

    }

    @FXML
    void onClickSubmit(ActionEvent event) {
        reservation.setPayment_state(1);
        reservation.setState(1);
        reservation.update();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    @Override
    public void setData(Map<String, Object> data) {
        if (data.containsKey("matricule")) {
            String matricule = (String) data.get("matricule");
            onReceiveMatricule(matricule);
            return;
        }

        if(data.containsKey("id_reservation")){
            int id_reservation = (int) data.get("id_reservation");
            onReceiveReservation(id_reservation);
            return;
        }
    }

    private void onReceiveMatricule(String matricule) {

        Voiture v = new Voiture();
        v.setMatricule(matricule);
        if(!v.readByMatricule()){
            return;
        }


        Member m = new Member();
        m.setId(v.getId_m());

        if(!m.read()){
            return;
        }

        TypeMember tm = new TypeMember();
        tm.setId(m.getId_member_type());

        if(!tm.read()){
            return;
        }

        //select last reservation
        Reservation r = new Reservation();
        r.setId_V(v.getId());

        if(!r.readByVoiture()){
            return;
        }


        Place p = new Place();
        p.setId(r.getId_place());

        if(!p.read()){
            return;
        }


        Parking pk = new Parking();
        pk.setId(p.getId_parking());

        if(!pk.read()){
            return;
        }

        this.voiture = v;
        this.membre = m;
        this.type_membre = tm;
        this.reservation = r;
        this.place = p;
        this.parking = pk;

        this.loadForm();

    }

    private void onReceiveReservation(int id_reservation) {

        Reservation r = new Reservation();
        r.setId(id_reservation);
        if(!r.read()){
            return;
        }

        Voiture v = new Voiture();
        v.setId(r.getId_V());
        if(!v.read()){
            return;
        }

        Member m = new Member();
        m.setId(v.getId_m());
        if(!m.read()){
            return;
        }

        TypeMember tm = new TypeMember();
        tm.setId(m.getId_member_type());
        if(!tm.read()){
            return;
        }

        Place p = new Place();
        p.setId(r.getId_place());

        if(!p.read()){
            return;
        }

        Parking pk = new Parking();
        pk.setId(p.getId_parking());

        if(!pk.read()){
            return;
        }

        this.voiture = v;
        this.membre = m;
        this.type_membre = tm;
        this.reservation = r;
        this.place = p;
        this.parking = pk;

        this.loadForm();

    }


    private void loadForm() {

        if(voiture == null){
            return;
        }

        if(membre == null){
            return;
        }

        if(type_membre == null){
            return;
        }

        if(reservation == null){
            return;
        }

        txt_matricule.setText(voiture.getMatricule());
        txt_nomprenom.setText(membre.getName() + " " + membre.getPrenom());
        txt_parking.setText(parking.getName());
        txt_num_place.setText(place.getId()+"");
        txt_typem.setText(type_membre.getLebelle());
        dateE.setValue(reservation.getDate_debut().toLocalDate());
        txt_timee.setText(
                        reservation.getDate_debut().getHour()
                        +":"
                        +reservation.getDate_debut().getMinute());

        //calculer le prix
        reservation.setDate_fin(LocalDateTime.now());
        reservation.setPrix(price());

        dateS.setValue(reservation.getDate_fin().toLocalDate());
        txt_times.setText(
                reservation.getDate_fin().getHour()
                        +":"
                        +reservation.getDate_fin().getMinute());

        txt_prix.setText(reservation.getPrix()+"");

    }

    public float price(){

        Duration duration = Duration.between(reservation.getDate_debut(),reservation.getDate_fin());

        float prix = 2;

        if(place.getTypePlaceStr().equals("gold")){
            prix = 5;
        }
        else if (place.getTypePlaceStr().equals("silver")){
            prix = 3;
        }

        prix *= duration.toHours()+1;

        if(membre.getMemberTypeStr().equals("gold")){
            prix -= prix * .30F;
        }
        else if (membre.getMemberTypeStr().equals("silver")){
            prix -= prix * .15F;
        }

        return prix;

    }
}
