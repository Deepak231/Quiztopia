package com.example.mahe.quiztopia.services;

import com.example.mahe.quiztopia.models.Followed_Topic;
import com.example.mahe.quiztopia.models.TopicDetail;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by MAHE on 3/3/2018.
 */

public interface TopicService {
    @GET("topics")
    Call<ArrayList<TopicDetail>> gettopic();

    @GET("followed_topics")
    Call<ArrayList<Followed_Topic>> getfollowedtopic();

    @POST("followed_topics")
    Call<Followed_Topic> createfollowedtopic(@Body Followed_Topic newtopic );

    @DELETE("followed_topics/{id}")
    Call<Void> deletetopic(@Path("id")int id );

}
