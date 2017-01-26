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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
