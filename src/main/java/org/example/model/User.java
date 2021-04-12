package org.example.model;

import org.example.App;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class User extends Model {

    //table name
    public String tableName() {
        return "users";
    }

    //felds
    private String utilisateur;
    private String motdepasse;

    //Constructors
    public User(){}

    public User(int id,String utilisateur,String motdepasse) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.motdepasse = motdepasse;
    }

    //Geter and seters
    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }

    //
    @Override
    public boolean readRow(Map<String, Object> row) {

        this.id = (int) row.get("id");
        this.utilisateur = (String) row.get("utilisateur");
        this.motdepasse = (String) row.get("motdepasse");

        return true;
    }

    @Override
    public Map<String,Object> toRow() {

        Map<String,Object> row = new HashMap<>();
        row.put("utilisateur",utilisateur);
        row.put("motdepasse",motdepasse);

        return row;
    }

    @Override
    public Model getInstance() {
        return new User();
    }

    //Custom methods

    public boolean checkUsernameAndPassword(){

        List<Object> params = new ArrayList<>();
        params.add(utilisateur);
        params.add(motdepasse);
        Object obj = App.db.executeScalar("SELECT count(*) FROM users WHERE utilisateur=? AND motdepasse=?",params);

        if(obj==null)
            return false;

        return (long)obj>0;
    }

}
