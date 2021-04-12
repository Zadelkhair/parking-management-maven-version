package org.example;

public class Middleware {

    private Auth auth;

    public Middleware(Auth auth) {
        auth = auth;
    }

    public void checkVisibles(String viewurl) {

        if(auth.isLoggedIn()){

        }

    }

    public void checkPermissions(String action){

        if(auth.isLoggedIn()){

        }

    }

}