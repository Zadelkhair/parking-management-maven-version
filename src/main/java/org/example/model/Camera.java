package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class Camera extends Model {

    //table name
    public String tableName() {
        return "cameras";
    }

    //felds
    private int id_equipement;

    private String url;


    //Constructors
    public Camera() {
    }

    public Camera(int id, int id_equipement) {
        this.id = id;
        this.id_equipement = id_equipement;

    }

    //Geter and seters
    public int getId_equipement() {
        return id_equipement;
    }

    public void setId_equipement(int id_equipement) {
        this.id_equipement = id_equipement;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
        this.id_equipement = (int) row.get("id_equipement");
        this.url = (String) row.get("url");

        return true;
    }

    @Override
    public Map<String, Object> toRow() {

        Map<String, Object> row = new HashMap<>();
        row.put("id_equipement", id_equipement);
        row.put("url", url);

        return row;
    }

    @Override
    public Model getInstance() {
        return new Camera();
    }

    public boolean create(String libelle) {
        //create or update equipement
        Equipement eq = new Equipement();
        eq.setId(this.getId_equipement());

        eq.setLibelle(libelle);

        boolean equipement_res = false;
        if(eq.isExist()){
            equipement_res = eq.update();
        }
        else{
            equipement_res = eq.create();
            this.setId_equipement(eq.getId());
            this.update();
        }

        if(!equipement_res)
            return false;

        return this.create();
    }

    public boolean update(String libelle) {
        //create or update equipement
        Equipement eq = new Equipement();
        eq.setId(this.getId_equipement());

        eq.setLibelle(libelle);

        boolean equipement_res = false;
        if(eq.isExist()){
            equipement_res = eq.update();
        }
        else{
            equipement_res = eq.create();
            this.setId_equipement(eq.getId());
            this.update();
        }

        if(!equipement_res)
            return false;

        return this.update();
    }

    //Custom methods
    public String getEquipementStr(){

        Equipement eq = new Equipement();
        eq.setId(this.getId_equipement());
        eq.read();

        return eq.getLibelle();
    }

}
