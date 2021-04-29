package org.example.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


public class Member extends Model {

    //table name
    public String tableName() {
        return "members";
    }

    //felds
	private String name;
	private String prenom;
	private java.sql.Date debut_abonnement;
	private java.sql.Date fin_abonnement;
	private int id_member_type;


    //Constructors
    public Member(){}

    public Member(int id,String name,String prenom,java.sql.Date debut_abonnement,java.sql.Date fin_abonnement,int id_member_type) {
        this.id = id;
		this.name = name;
		this.prenom = prenom;
		this.debut_abonnement = debut_abonnement;
		this.fin_abonnement = fin_abonnement;
		this.id_member_type = id_member_type;

    }

    //Geter and seters
	public String getName() {
	   return name;
	}
	public void setName(String name) {
	   this.name = name;
	}


	public String getPrenom() {
	   return prenom;
	}
	public void setPrenom(String prenom) {
	   this.prenom = prenom;
	}


	public java.sql.Date getDebut_abonnement() {
	   return debut_abonnement;
	}
	public void setDebut_abonnement(java.sql.Date debut_abonnement) {
	   this.debut_abonnement = debut_abonnement;
	}


	public java.sql.Date getFin_abonnement() {
	   return fin_abonnement;
	}
	public void setFin_abonnement(java.sql.Date fin_abonnement) {
	   this.fin_abonnement = fin_abonnement;
	}


	public int getId_member_type() {
	   return id_member_type;
	}
	public void setId_member_type(int id_member_type) {
	   this.id_member_type = id_member_type;
	}


	public String getMemberTypeStr(){

    	TypeMember tm = new TypeMember();
    	tm.setId(this.getId_member_type());
    	tm.read();

    	return tm.getLebelle();
	}

    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
		this.name = (String) row.get("name");
		this.prenom = (String) row.get("prenom");
		this.debut_abonnement = (java.sql.Date) row.get("debut_abonnement");
		this.fin_abonnement = (java.sql.Date) row.get("fin_abonnement");
		this.id_member_type = (int) row.get("id_member_type");


        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
		row.put("name",name);
		row.put("prenom",prenom);
		row.put("debut_abonnement",debut_abonnement);
		row.put("fin_abonnement",fin_abonnement);
		row.put("id_member_type",id_member_type);


        return row;
    }

    @Override
    public Model getInstance() {
        return new Member();
    }

    //Custom methods

}
