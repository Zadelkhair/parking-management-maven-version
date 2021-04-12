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


    //Constructors
    public Camera(){}

    public Camera(int id,int id_equipement) {
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




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.id_equipement = (int) row.get("id_equipement");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("id_equipement",id_equipement);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Camera();
    }

    //Custom methods

}
