package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class Parking extends Model {

    //table name
    public String tableName() {
        return "parkings";
    }

    //felds
	private String name;
	private int nombre_total;
	private double surface;


    //Constructors
    public Parking(){}

    public Parking(int id,String name,int nombre_total,double surface) {
        this.id = id;
		this.name = name;
		this.nombre_total = nombre_total;
		this.surface = surface;

    }

    //Geter and seters
	public String getName() {
	   return name;
	}
	public void setName(String name) {
	   this.name = name;
	}


	public int getNombre_total() {
	   return nombre_total;
	}
	public void setNombre_total(int nombre_total) {
	   this.nombre_total = nombre_total;
	}


	public double getSurface() {
	   return surface;
	}
	public void setSurface(double surface) {
	   this.surface = surface;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.name = (String) row.get("name");
		this.nombre_total = (int) row.get("nombre_total");
		this.surface = (double) row.get("surface");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("name",name);
		row.put("nombre_total",nombre_total);
		row.put("surface",surface);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Parking();
    }

    //Custom methods

}
