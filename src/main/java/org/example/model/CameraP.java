package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class CameraP extends Model {

    //table name
    public String tableName() {
        return "cameras_p";
    }

    //felds
	private int id_camera;
	private int id_place;


    //Constructors
    public CameraP(){}

    public CameraP(int id,int id_camera,int id_place) {
        this.id = id;
		this.id_camera = id_camera;
		this.id_place = id_place;

    }

    //Geter and seters
	public int getId_camera() {
	   return id_camera;
	}
	public void setId_camera(int id_camera) {
	   this.id_camera = id_camera;
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
		this.id_camera = (int) row.get("id_camera");
		this.id_place = (int) row.get("id_place");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("id_camera",id_camera);
		row.put("id_place",id_place);


        return row;
    }

    @Override
    public Model getInstance() {
        return new CameraP();
    }

    //Custom methods

}
