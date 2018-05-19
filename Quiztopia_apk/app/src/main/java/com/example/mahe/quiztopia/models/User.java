package com.example.mahe.quiztopia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MAHE on 2/25/2018.
 */

public class User {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("lvl")
    @Expose
    private int lvl;

    @SerializedName("exp")
    @Expose
    private int exp;

    @SerializedName("ll")
    @Expose
    private String ll;

    public User() {}

    // Getters

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password; }


    public int getLvl() { return lvl; }

    public int getExp() { return exp; }

    public String getLl() {
        return ll;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) { this.password = password; }

    public void setLvl(int lvl) { this.lvl = lvl; }

    public void setExp(int exp) { this.exp = exp; }

    public void setLl(String ll) {
        this.ll = ll;
    }
}
