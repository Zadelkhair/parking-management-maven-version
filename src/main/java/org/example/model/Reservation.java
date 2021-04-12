package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class Reservation extends Model {

    //table name
    public String tableName() {
        return "reservations";
    }

    //felds
	private java.sql.Date date_debut;
	private java.sql.Date date_fin;
	private double prix;
	private int id_V;
	private int id_place;


    //Constructors
    public Reservation(){}

    public Reservation(int id,java.sql.Date date_debut,java.sql.Date date_fin,double prix,int id_V,int id_place) {
        this.id = id;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
		this.prix = prix;
		this.id_V = id_V;
		this.id_place = id_place;

    }

    //Geter and seters
	public java.sql.Date getDate_debut() {
	   return date_debut;
	}
	public void setDate_debut(java.sql.Date date_debut) {
	   this.date_debut = date_debut;
	}


	public java.sql.Date getDate_fin() {
	   return date_fin;
	}
	public void setDate_fin(java.sql.Date date_fin) {
	   this.date_fin = date_fin;
	}


	public double getPrix() {
	   return prix;
	}
	public void setPrix(double prix) {
	   this.prix = prix;
	}


	public int getId_V() {
	   return id_V;
	}
	public void setId_V(int id_V) {
	   this.id_V = id_V;
	}


	public int getId_place() {
	   return id_place;
	}
	public void setId_place(int id_place) {
	   this.id_place = id_place;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.date_debut = (java.sql.Date) row.get("date_debut");
		this.date_fin = (java.sql.Date) row.get("date_fin");
		this.prix = (double) row.get("prix");
		this.id_V = (int) row.get("id_V");
		this.id_place = (int) row.get("id_place");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("date_debut",date_debut);
		row.put("date_fin",date_fin);
		row.put("prix",prix);
		row.put("id_V",id_V);
		row.put("id_place",id_place);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Reservation();
    }

    //Custom methods

}
