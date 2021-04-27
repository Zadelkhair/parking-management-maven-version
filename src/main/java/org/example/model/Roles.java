package org.example.model;

import java.util.HashMap;
import java.util.Map;

public class Roles extends Model{
    //table name
    public String tableName() {
        return "roles";
    }

    //felds
    private String name;


    //Constructors
    public Roles(){}

    public Roles(int id,String libelle) {
        this.id = id;
        this.name = libelle;

    }

    //Geter and seters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }




    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
        this.name = (String) row.get("name");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
        row.put("name",name);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Roles();
    }

    //Custom methods

}
