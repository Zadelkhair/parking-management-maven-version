package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class Guichet extends Model {

    //table name
    public String tableName() {
        return "guichets";
    }

    //felds
	private String lebelle;
	private int id_equipement;


    //Constructors
    public Guichet(){}

    public Guichet(int id,String lebelle,int id_equipement) {
        this.id = id;
		this.lebelle = lebelle;
		this.id_equipement = id_equipement;

    }

    //Geter and seters
	public String getLebelle() {
	   return lebelle;
	}
	public void setLebelle(String lebelle) {
	   this.lebelle = lebelle;
	}


	public int getId_equipement() {
	   return id_equipement;
	}
	public void setId_equipement(int id_equipement) {
	   this.id_equipement = id_equipement;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.lebelle = (String) row.get("lebelle");
		this.id_equipement = (int) row.get("id_equipement");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("lebelle",lebelle);
		row.put("id_equipement",id_equipement);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Guichet();
    }

    //Custom methods

}
