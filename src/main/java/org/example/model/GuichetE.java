package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class GuichetE extends Model {

    //table name
    public String tableName() {
        return "guichets_e";
    }

    //felds
	private int id_guichet;
	private int id_entrer;


    //Constructors
    public GuichetE(){}

    public GuichetE(int id,int id_guichet,int id_entrer) {
        this.id = id;
		this.id_guichet = id_guichet;
		this.id_entrer = id_entrer;

    }

    //Geter and seters
	public int getId_guichet() {
	   return id_guichet;
	}
	public void setId_guichet(int id_guichet) {
	   this.id_guichet = id_guichet;
	}


	public int getId_entrer() {
	   return id_entrer;
	}
	public void setId_entrer(int id_entrer) {
	   this.id_entrer = id_entrer;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.id_guichet = (int) row.get("id_guichet");
		this.id_entrer = (int) row.get("id_entrer");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("id_guichet",id_guichet);
		row.put("id_entrer",id_entrer);


        return row;
    }

    @Override
    public Model getInstance() {
        return new GuichetE();
    }

    //Custom methods


}
