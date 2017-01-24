package com.model;

/**
 * Created by sunsun on 24/1/17.
 */

public class Usuario {

    public String userName;
    public String userMail;
    public int points;
    public int level;

    public Usuario(){
    }

    public Usuario(String userName) {
        this.userName = userName;
    }

    public Usuario(String userName, String userMail) {
        this.userName = userName;
        this.userMail = userMail;
    }

}
