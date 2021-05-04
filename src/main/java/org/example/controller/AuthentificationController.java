package org.example.controller;


import org.example.model.User;

public class AuthentificationController {

    private User user;

    public User getUser() {
        return user;
    }

    public boolean registring(String utilisateur, String motdepasse){

        User user = new User(-1,utilisateur,motdepasse);
        return user.create();

    }

    public boolean login(String utilisateur,String motdepasse){

        this.user = new User(-1,utilisateur,motdepasse);

        if(!user.checkUsernameAndPassword())
            return false;

        this.user.readByUtilisateur();

        return true;
    }

}