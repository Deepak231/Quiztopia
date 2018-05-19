package com.example.mahe.quiztopia.services;

import com.example.mahe.quiztopia.models.Ranking;
import com.example.mahe.quiztopia.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Created by MAHE on 4/3/2018.
 */

public interface RankingService {

    @GET("rankings")
    Call<ArrayList<Ranking>> getrankings();

    @FormUrlEncoded
    @PUT("rankings")
    Call<Ranking> updaterankings (
            @Field("username")String username,
            @Field("rank1")int rank1,
            @Field("rank2")int rank2,
            @Field("rank3")int rank3
    );

}
