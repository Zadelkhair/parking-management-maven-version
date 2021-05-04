
package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.App;
import org.example.model.Member;
import org.example.model.Reservation;
import org.example.model.Voiture;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

public class HistoriqueViewController implements Initializable {

    @FXML
    private DatePicker date_d;

    @FXML
    private TextField debut_time;

    @FXML
    private DatePicker date_f;

    @FXML
    private TextField fin_time;

    @FXML
    private TableView<Reservation> tablev_history;

    private ArrayList<Reservation> reservations;
    private Reservation selectedReservation;

    @FXML
    void onClickRechercher(ActionEvent event) {

        if(date_d.getValue()==null)
            return;

        if(date_f.getValue()==null)
            return;

        if(debut_time.getText().split(":").length < 2)
            return;

        if(fin_time.getText().split(":").length < 2)
            return;

        if(debut_time.getText().split(":")[0].length()==0)
            return;

        if(debut_time.getText().split(":")[1].length()==0)
            return;

        if(fin_time.getText().split(":")[0].length()==0)
            return;

        if(fin_time.getText().split(":")[1].length()==0)
            return;

        try {

            LocalDateTime dateTimeD = LocalDateTime.of(
                    date_d.getValue().getYear(),
                    date_d.getValue().getMonthValue(),
                    date_d.getValue().getDayOfMonth(),
                    Integer.parseInt(debut_time.getText().split(":")[0]),
                    Integer.parseInt(debut_time.getText().split(":")[1])
            );

            LocalDateTime dateTimeF = LocalDateTime.of(
                    date_f.getValue().getYear(),
                    date_f.getValue().getMonthValue(),
                    date_f.getValue().getDayOfMonth(),
                    Integer.parseInt(fin_time.getText().split(":")[0]),
                    Integer.parseInt(fin_time.getText().split(":")[1])
            );

            loadReservationsData(dateTimeD,dateTimeF);
            loadTableviewReservationsData();

        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }

    private void loadReservationsData(LocalDateTime dateTimeD, LocalDateTime dateTimeF) {
        reservations = new ArrayList<>();

        List<Map<String, Object>> all = (new Reservation()).getAllBetween(true,dateTimeD,dateTimeF);

        for (Map<String, Object> row : all) {
            Reservation r = new Reservation();
            r.readRow(row);
            reservations.add(r);
        }
    }

    @FXML
    void onClickLoad(ActionEvent event) {
        this.loadReservationsData();
        this.loadTableviewReservationsData();
    }

    @FXML
    void onClickPayer(ActionEvent event) {
        if (selectedReservation!=null) {
            if(selectedReservation.getPayment_state() != 1){
                Map<String, Object> data = new HashMap<>();
                data.put("id_reservation", selectedReservation.getId());
                App.viewController.navigateTo("payment.fxml", null, data);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selectionne une sortie pour la detection la sortie d'un vehicule");
        }
    }

    @FXML
    void onMouseClickTableView(MouseEvent event) {

        Object obj = tablev_history.getSelectionModel().getSelectedItem();
        if (event.getClickCount() == 2 && obj != null) {
            if(reservations.indexOf(obj) >= 0){
                clearInputs();
                selectedReservation = (Reservation)obj;
                date_d.setValue(selectedReservation.getDate_debut().toLocalDate());
                debut_time.setText(
                                selectedReservation.getDate_debut().getHour() +
                                ":" +
                                selectedReservation.getDate_debut().getMinute()
                );

                if(selectedReservation.getDate_fin()!=null){
                    date_f.setValue(selectedReservation.getDate_fin().toLocalDate());
                    fin_time.setText(
                            selectedReservation.getDate_fin().getHour() +
                                    ":" +
                                    selectedReservation.getDate_fin().getMinute()
                    );
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableviewReservationsColumns();
        loadReservationsData();
        loadTableviewReservationsData();
    }

    public void loadReservationsData() {
        reservations = new ArrayList<>();

        List<Map<String, Object>> all = (new Reservation()).getAll(true);

        for (Map<String, Object> row : all) {
            Reservation r = new Reservation();
            r.readRow(row);
            reservations.add(r);
        }
    }

    public void loadTableviewReservationsColumns() {
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_history.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Reservation()).toRow().entrySet()) {

            if (entry.getKey() == "id_V") {
                column = new TableColumn("voiture");
                column.setCellValueFactory(new PropertyValueFactory<>("voitureStr"));
                tablev_history.getColumns().add(column);

                continue;
            }

            if (entry.getKey() == "id_place") {

                column = new TableColumn("place");
                column.setCellValueFactory(new PropertyValueFactory<>("placeStr"));
                tablev_history.getColumns().add(column);

                continue;
            }

            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_history.getColumns().add(column);
        }
    }

    public void loadTableviewReservationsData() {
        tablev_history.getColumns().get(0).setVisible(false);
        tablev_history.getColumns().get(0).setVisible(true);
        ObservableList<Reservation> reservationObservableList = FXCollections.observableList(reservations);
        tablev_history.setItems(reservationObservableList);
    }

    public void clearInputs(){
        date_f.setValue(null);
        date_d.setValue(null);
        debut_time.clear();
        fin_time.clear();
    }

}
