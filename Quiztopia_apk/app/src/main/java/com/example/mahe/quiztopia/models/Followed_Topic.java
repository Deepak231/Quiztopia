package com.example.mahe.quiztopia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MAHE on 3/4/2018.
 */

public class Followed_Topic {
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("topics")
    @Expose
    private String foll_top;

    @SerializedName("id")
    @Expose
    private int id;

    public String getUsername() {
        return username;
    }

    public String getFoll_top() {
        return foll_top;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFoll_top(String foll_top) {
        this.foll_top = foll_top;
    }

    public int getId() {
        return id;
    }
}
