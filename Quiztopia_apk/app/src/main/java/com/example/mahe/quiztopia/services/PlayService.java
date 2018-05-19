package com.example.mahe.quiztopia.services;

import com.example.mahe.quiztopia.models.PlayDetail;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by MAHE on 3/6/2018.
 */

public interface PlayService {
    @GET("General_Knowledge")
    Call<ArrayList<PlayDetail>> getplay1();

    @GET("Android")
    Call<ArrayList<PlayDetail>> getplay2();

    @GET("Basic_Math")
    Call<ArrayList<PlayDetail>> getplay3();
}
