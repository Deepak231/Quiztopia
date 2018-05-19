package com.example.mahe.quiztopia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahe.quiztopia.Adapter.ListChipsAdapter;
import com.example.mahe.quiztopia.models.TopicDetail;
import com.example.mahe.quiztopia.services.ServiceBuilder;
import com.example.mahe.quiztopia.services.TopicService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Explore extends Fragment {

    ArrayList<String> chipstitle = new ArrayList<>();
    ArrayList<String[]> chipsArray = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_explore,container,false);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        TopicService topicName = ServiceBuilder.buildService(TopicService.class);
        final Call<ArrayList<TopicDetail>> jsonObjectCall = topicName.gettopic();

        jsonObjectCall.enqueue(new Callback<ArrayList<TopicDetail>>() {
            @Override
            public void onResponse(Call<ArrayList<TopicDetail>> call, Response<ArrayList<TopicDetail>> response) {
                int num = response.body().size();
                for (int i = 0 ; i < num ; i++) {
                    chipstitle.add(response.body().get(i).getMain());
                    int num1 = response.body().get(i).getSub().length;
                    String string[] = new String[num1];
                    for (int j = 0 ; j < num1 ; j++) {
                        string[j] = response.body().get(i).getSub()[j];
                    }
                    chipsArray.add(string);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<TopicDetail>> call, Throwable t) {

            }
        });
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView chipRecyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);

        chipRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        chipRecyclerView.setAdapter(new ListChipsAdapter(chipsArray,chipstitle));

    }


}
