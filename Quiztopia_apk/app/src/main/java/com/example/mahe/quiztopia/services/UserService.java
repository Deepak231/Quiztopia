package com.example.mahe.quiztopia.services;

import com.example.mahe.quiztopia.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by MAHE on 2/25/2018.
 */

public interface UserService {

    @POST("users")
    Call<User> createuser(@Body User newuser);

    @GET("users")
    Call<ArrayList<User>> getuser();

    @FormUrlEncoded
    @PUT("users")
    Call<User> updateuser (
        @Field("username")String username,
        @Field("lvl")int lvl,
        @Field("exp")int exp,
        @Field("ll")String ll
    );
}
