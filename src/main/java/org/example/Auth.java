package org.example;

import org.example.model.User;

public class Auth {

    User authentificated_user;

    Auth(){
        this.authentificated_user = new User();
    }

    public void setAuthentificatedUser(User auth) {
        System.out.println(authentificated_user.getUtilisateur());
        this.authentificated_user = auth;
    }

    public boolean isLoggedIn(){
        System.out.println(authentificated_user.getId());
        return authentificated_user.getId() != -1;
    }

    public String getUsername() {

//        if(!isLoggedIn())
//            return null;

        return authentificated_user.getUtilisateur();
    }

}
