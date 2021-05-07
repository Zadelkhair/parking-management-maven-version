package org.example.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.example.App;
import org.example.model.Place;
import org.example.model.Reservation;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class ReservationStep2ViewController implements IReceiveData, Initializable {

    private List<Reservation> reservations;

    @FXML
    private CheckBox chk_multireservation;

    @FXML
    private Label lbl_place;

    @FXML
    private Label lbl_selected_place;

    @FXML
    private FlowPane flowpanel;

    private int voiture_id = -1;
    private ArrayList<Place> places;

    public int getVoiture_id() {
        return voiture_id;
    }

    public void setVoiture_id(int voiture_id) {
        this.voiture_id = voiture_id;
    }

    private Reservation reservation;

    private boolean multireservation = false;

    public boolean isMultireservation() {
        multireservation = chk_multireservation.isSelected();
        return multireservation;
    }

    @FXML
    void onActionChk(ActionEvent event) {
        reservation=null;
        reservations.clear();
        PositionCardViewController.deselectAll();
    }

    @Override
    public void setData(Map<String, Object> data) {
        if (data.containsKey("voiture_id")) {
            this.setVoiture_id((Integer) data.get("voiture_id"));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadPlaces();

        try {
            for (Place p : places) {
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("position_card.fxml"));

                Parent root = fxmlLoader.load();
                root.setUserData(fxmlLoader);

                PositionCardViewController positionCardViewController = (PositionCardViewController) fxmlLoader.getController();
                positionCardViewController.setNum(p.getId());
                positionCardViewController.setReserved(p.isReserverd());

                root.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {

                        AnchorPane place = (AnchorPane) mouseEvent.getSource();
                        FXMLLoader place_fxmlLoader = (FXMLLoader) place.getUserData();
                        PositionCardViewController positionCardViewController = (PositionCardViewController) place_fxmlLoader.getController();

                        if (positionCardViewController != null) {

                            if (positionCardViewController.isReserved())
                                return;

                            reservation = new Reservation();
                            reservation.setDate_debut(LocalDateTime.now());
                            reservation.setId_V(voiture_id);
                            reservation.setId_place(positionCardViewController.getNum());
                            reservation.setPayment_state(0);
                            reservation.setState(0);

                            if (isMultireservation()) {
                                if (reservations == null)
                                    reservations = new ArrayList<>();

                                boolean reservation_exisit = false;
                                for (Reservation r : reservations) {
                                    System.out.println("Remove " + r.getId_place());
                                    if (r.getId_place() == reservation.getId_place()) {
                                        System.out.println("Remove " + r.getId_place());
                                        reservations.remove(r);
                                        positionCardViewController.selectANDdeselect(isMultireservation());
                                        reservation_exisit = true;
                                        break;
                                    }
                                }

                                if (!reservation_exisit) {
                                    reservations.add(reservation);
                                }

                                String selected_places = "";
                                for (Reservation r : reservations) {
                                    selected_places += r.getId_place() + " , ";
                                }
                                if (selected_places.length() >= 3)
                                    selected_places = selected_places.substring(0, selected_places.length() - 3);
                                ;
                                lbl_selected_place.setText("Select a places [" + reservations.size() + "] : " + selected_places);

                                if (reservation_exisit)
                                    return;
                            } else {
                                reservations = new ArrayList<>();
                                lbl_selected_place.setText("Select a place : " + reservation.getId_place() + "");
                            }

                            positionCardViewController.selectANDdeselect(isMultireservation());

                        }
                    }
                });

                flowpanel.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void loadPlaces() {
        places = new ArrayList<>();

        List<Map<String, Object>> all = (new Place()).getAll(true);

        for (Map<String, Object> row : all) {
            Place p = new Place();
            p.readRow(row);
            places.add(p);
        }
    }

    public void onClickConfirmer(MouseEvent mouseEvent) {
        if (reservation == null)
            return;

        if (isMultireservation()) {
            for (Reservation r : reservations) {
                r.create();
            }
        } else {
            reservation.create();
        }

        App.viewController.navigateTo("historique.fxml");

    }


}
