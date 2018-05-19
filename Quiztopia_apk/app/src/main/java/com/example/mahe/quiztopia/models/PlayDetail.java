package com.example.mahe.quiztopia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MAHE on 3/6/2018.
 */

public class PlayDetail {

    @SerializedName("quest")
    @Expose
    private String quest;
    @SerializedName("opt")
    @Expose
    private String[] opt;

    @SerializedName("ans")
    @Expose
    private String ans;

    public String getQuest() {
        return quest;
    }

    public String[] getOpt() {
        return opt;
    }

    public String getAns() {
        return ans;
    }
}
