package org.example.model;

import java.util.HashMap;
import java.util.Map;


public class TypeMember extends Model {

    //table name
    public String tableName() {
        return "type_members";
    }

    //felds
	private String lebelle;


    //Constructors
    public TypeMember(){}

    public TypeMember(int id,String lebelle) {
        this.id = id;
		this.lebelle = lebelle;

    }

    //Geter and seters
	public String getLebelle() {
	   return lebelle;
	}
	public void setLebelle(String lebelle) {
	   this.lebelle = lebelle;
	}




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.lebelle = (String) row.get("lebelle");

        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("lebelle",lebelle);

        return row;
    }

    @Override
    public Model getInstance() {
        return new TypeMember();
    }

    //Custom methods

}
