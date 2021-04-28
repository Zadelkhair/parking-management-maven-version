
package org.example.controller;

        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.Initializable;
        import javafx.scene.control.DatePicker;
        import javafx.scene.control.TableColumn;
        import javafx.scene.control.TableView;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.input.MouseEvent;
        import org.example.model.Member;
        import org.example.model.Reservation;

        import java.net.URL;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Map;
        import java.util.ResourceBundle;

public class HistoriqueViewController implements Initializable {

    @FXML
    private DatePicker date_d;

    @FXML
    private DatePicker date_f;

    @FXML
    private TableView<Reservation> tablev_history;

    private ArrayList<Reservation> reservations;

    @FXML
    void onClickRecherhcer(ActionEvent event) {

    }

    @FXML
    void onMouseClickTableView(MouseEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadTableviewReservationsColumns();
        loadReservationsData();
        loadTableviewReservationsData();
    }

    public void loadReservationsData(){
        reservations = new ArrayList<>();

        List<Map<String,Object>> all = (new Reservation()).getAll(true);

        for (Map<String,Object> row:all) {
            Reservation r = new Reservation();
            r.readRow(row);
            reservations.add(r);
        }
    }

    public void loadTableviewReservationsColumns(){
        TableColumn column = new TableColumn("id");
        column.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablev_history.getColumns().add(column);
        for (Map.Entry<String, Object> entry : (new Reservation()).toRow().entrySet()) {
            column = new TableColumn(entry.getKey());
            column.setCellValueFactory(new PropertyValueFactory<>(entry.getKey()));
            tablev_history.getColumns().add(column);
        }
    }

    public void loadTableviewReservationsData(){
        tablev_history.getColumns().get(0).setVisible(false);
        tablev_history.getColumns().get(0).setVisible(true);
        ObservableList<Reservation> reservationObservableList = FXCollections.observableList(reservations);
        tablev_history.setItems(reservationObservableList);
    }



}
