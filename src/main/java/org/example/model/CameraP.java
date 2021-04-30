package org.example.model;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.util.HashMap;
import java.util.Map;

public class CameraP extends Model {

    //table name
    public String tableName() {
        return "cameras_p";
    }

    //felds
    private int id_camera = -1;
    private int id_place = -1;

    //Constructors
    public CameraP() {
    }

    public CameraP(int id, int id_camera, int id_place) {
        this.id = id;
        this.id_camera = id_camera;
        this.id_place = id_place;

    }

    //Geter and seters
    public int getId_camera() {
        return id_camera;
    }

    public void setId_camera(int id_camera) {
        this.id_camera = id_camera;
    }

    public int getId_place() {
        return id_place;
    }

    public void setId_place(int id_place) {
        this.id_place = id_place;
    }

    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
        this.id_camera = (int) row.get("id_camera");
        this.id_place = (int) row.get("id_place");


        return true;
    }

    @Override
    public Map<String, Object> toRow() {

        Map<String, Object> row = new HashMap<>();
        row.put("id_camera", id_camera);
        row.put("id_place", id_place);


        return row;
    }

    @Override
    public Model getInstance() {
        return new CameraP();
    }



    public String getEquipementStr() {
        Camera c = new Camera();
        c.setId(this.getId_camera());

        if(!c.read()){
            return "";
        }

        Equipement eq = new Equipement();
        eq.setId(c.getId_equipement());
        eq.read();

        return eq.getLibelle();
    }

    public String getPlaceStr(){
        Place p = new Place();
        p.setId(this.getId_place());
        p.read();

        return p.getId() + " ["+p.getParkingStr()+"]";
    }

    public String getCameraUrl(){
        Camera c = new Camera();
        c.setId(this.getId_camera());
        c.read();

        return c.getUrl();
    }

    public boolean update(String libelle, String url) {
        this.read();

        //create or update camera
        Camera c = new Camera();
        c.setId(this.getId_camera());

        c.setUrl(url);

        boolean camera_rs = false;
        if(c.isExist()){
            camera_rs = c.update(libelle);
        }
        else{
            camera_rs = c.create(libelle);
            this.setId_camera(c.getId());
        }

        if(!camera_rs)
            return false;

        return this.update();
    }

    public boolean create(String libelle, String url) {
        this.read();

        //create or update camera
        Camera c = new Camera();
        c.setId(this.getId_camera());

        c.setUrl(url);

        boolean camera_rs = false;
        if(c.isExist()){
            camera_rs = c.update(libelle);
        }
        else{
            camera_rs = c.create(libelle);
            this.setId_camera(c.getId());
        }

        if(!camera_rs)
            return false;

        return this.create();
    }

}
