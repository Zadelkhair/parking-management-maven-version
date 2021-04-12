package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class Equipement extends Model {

    //table name
    public String tableName() {
        return "equipements";
    }

    //felds
	private String libelle;


    //Constructors
    public Equipement(){}

    public Equipement(int id,String libelle) {
        this.id = id;
		this.libelle = libelle;

    }

    //Geter and seters
	public String getLibelle() {
	   return libelle;
	}
	public void setLibelle(String libelle) {
	   this.libelle = libelle;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.libelle = (String) row.get("libelle");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("libelle",libelle);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Equipement();
    }

    //Custom methods

}
