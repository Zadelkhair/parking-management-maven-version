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


    //Constructors
    public Sortie(){}

    public Sortie(int id,String libelle,int id_parking) {
        this.id = id;
		this.libelle = libelle;
		this.id_parking = id_parking;

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




    //

    public String getParkingStr(){

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


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("libelle",libelle);
		row.put("id_parking",id_parking);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Sortie();
    }

    //Custom methods

}
