package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class Sortie extends Model {

    //table name
    public String tableName() {
        return "sorties";
    }

    //felds
    private String libelle;
    private int id_parking;
    private int id_camera;


    //Constructors
    public Sortie() {
    }

    public Sortie(int id, String libelle, int id_parking, int id_camera) {
        this.id = id;
        this.libelle = libelle;
        this.id_parking = id_parking;
        this.id_camera = id_camera;

    }

    //Geter and seters
    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }


    public int getId_parking() {
        return id_parking;
    }

    public void setId_parking(int id_parking) {
        this.id_parking = id_parking;
    }

    public int getId_camera() {
        return id_camera;
    }

    public void setId_camera(int id_camera) {
        this.id_camera = id_camera;
    }


//

    public String getParkingStr() {

        Parking tm = new Parking();
        tm.setId(this.getId_parking());
        tm.read();

        return tm.getName();
    }

    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
        this.libelle = (String) row.get("libelle");
        this.id_parking = (int) row.get("id_parking");

        if (row.get("id_camera") != null)
            this.id_camera = (int) row.get("id_camera");

        return true;
    }

    @Override
    public Map<String, Object> toRow() {

        Map<String, Object> row = new HashMap<>();
        row.put("libelle", libelle);
        row.put("id_parking", id_parking);
        row.put("id_camera", id_camera);

        return row;

    }

    @Override
    public Model getInstance() {
        return new Sortie();
    }

    //Custom methods
    public String getCameraStr(){
        Camera c = new Camera();
        c.setId(this.getId_camera());
        c.read();

        return c.getEquipementStr()+ "(" + c.getUrl() +")";
    }

    public String getCameraUrlStr(){
        Camera c = new Camera();
        c.setId(this.getId_camera());
        c.read();

        return c.getUrl() ;
    }

}
