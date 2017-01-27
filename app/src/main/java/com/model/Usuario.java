package com.model;

import java.util.HashMap;

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

    public Usuario(String userName, String userMail, int points, int level) {
        this.userName = userName;
        this.userMail = userMail;
        this.points = points;
        this.level = level;
    }

    public HashMap<String, Object> toMap() {

        HashMap<String, Object> result = new HashMap<>();

        result.put("level", level);

        result.put("points", points);

        result.put("userMail", userMail);

        result.put("userName", userName);

        return result;
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

    @Override
    public String toString() {
        return "Usuario: Nombre: " + this.getUserName() +
                " Mail: " + this.getUserMail() +
                " Level: " + this.getLevel() +
                " Points: "+ this.getPoints();
    }
}
