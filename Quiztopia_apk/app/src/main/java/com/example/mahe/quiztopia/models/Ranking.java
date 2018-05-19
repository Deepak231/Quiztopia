package com.example.mahe.quiztopia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MAHE on 4/3/2018.
 */

public class Ranking {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("rank1")
    @Expose
    private int rank1;

    @SerializedName("rank2")
    @Expose
    private int rank2;

    @SerializedName("rank3")
    @Expose
    private int rank3;

    public String getUsername() {
        return username;
    }

    public int getRank1() {
        return rank1;
    }

    public int getRank2() {
        return rank2;
    }

    public int getRank3() {
        return rank3;
    }
}
