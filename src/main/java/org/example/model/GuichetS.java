package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class GuichetS extends Model {

    //table name
    public String tableName() {
        return "guichets_s";
    }

    //felds
	private int id_guichet;
	private int id_sortie;


    //Constructors
    public GuichetS(){}

    public GuichetS(int id,int id_guichet,int id_sortie) {
        this.id = id;
		this.id_guichet = id_guichet;
		this.id_sortie = id_sortie;

    }

    //Geter and seters
	public int getId_guichet() {
	   return id_guichet;
	}
	public void setId_guichet(int id_guichet) {
	   this.id_guichet = id_guichet;
	}


	public int getId_sortie() {
	   return id_sortie;
	}
	public void setId_sortie(int id_sortie) {
	   this.id_sortie = id_sortie;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.id_guichet = (int) row.get("id_guichet");
		this.id_sortie = (int) row.get("id_sortie");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("id_guichet",id_guichet);
		row.put("id_sortie",id_sortie);


        return row;
    }

    @Override
    public Model getInstance() {
        return new GuichetS();
    }

    //Custom methods

}
