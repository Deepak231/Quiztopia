package com.example.mahe.quiztopia.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MAHE on 3/3/2018.
 */

public class TopicDetail {

    @SerializedName("main")
    @Expose
    private String main;

    @SerializedName("sub")
    @Expose
    private String[] sub;

    public String getMain() {
        return main;
    }

    public String[] getSub() {
        return sub;
    }

}
