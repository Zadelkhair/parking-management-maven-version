package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class Voiture extends Model {

    //table name
    public String tableName() {
        return "voitures";
    }

    //felds
	private String matricule;
	private String colour;
	private String la_marque;
	private int id_m;


    //Constructors
    public Voiture(){}

    public Voiture(int id,String matricule,String colour,String la_marque,int id_m) {
        this.id = id;
		this.matricule = matricule;
		this.colour = colour;
		this.la_marque = la_marque;
		this.id_m = id_m;

    }

    //Geter and seters
	public String getMatricule() {
	   return matricule;
	}
	public void setMatricule(String matricule) {
	   this.matricule = matricule;
	}


	public String getColour() {
	   return colour;
	}
	public void setColour(String colour) {
	   this.colour = colour;
	}


	public String getLa_marque() {
	   return la_marque;
	}
	public void setLa_marque(String la_marque) {
	   this.la_marque = la_marque;
	}


	public int getId_m() {
	   return id_m;
	}
	public void setId_m(int id_m) {
	   this.id_m = id_m;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.matricule = (String) row.get("matricule");
		this.colour = (String) row.get("colour");
		this.la_marque = (String) row.get("la_marque");
		this.id_m = (int) row.get("id_m");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("matricule",matricule);
		row.put("colour",colour);
		row.put("la_marque",la_marque);
		row.put("id_m",id_m);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Voiture();
    }

    //Custom methods

}
