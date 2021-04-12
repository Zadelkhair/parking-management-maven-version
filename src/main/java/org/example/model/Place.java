package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class Place extends Model {

    //table name
    public String tableName() {
        return "places";
    }

    //felds
	private int id_TP;
	private int id_parking;


    //Constructors
    public Place(){}

    public Place(int id,int id_TP,int id_parking) {
        this.id = id;
		this.id_TP = id_TP;
		this.id_parking = id_parking;

    }

    //Geter and seters
	public int getId_TP() {
	   return id_TP;
	}
	public void setId_TP(int id_TP) {
	   this.id_TP = id_TP;
	}


	public int getId_parking() {
	   return id_parking;
	}
	public void setId_parking(int id_parking) {
	   this.id_parking = id_parking;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.id_TP = (int) row.get("id_TP");
		this.id_parking = (int) row.get("id_parking");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("id_TP",id_TP);
		row.put("id_parking",id_parking);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Place();
    }

    //Custom methods

}
