package com.example.mahe.quiztopia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.quiztopia.Explore;
import com.example.mahe.quiztopia.MainPage;
import com.example.mahe.quiztopia.Play;
import com.example.mahe.quiztopia.R;
import com.example.mahe.quiztopia.RankList;
import com.example.mahe.quiztopia.Topics;
import com.example.mahe.quiztopia.models.Followed_Topic;
import com.example.mahe.quiztopia.services.ServiceBuilder;
import com.example.mahe.quiztopia.services.TopicService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by MAHE on 3/2/2018.
 */

public class ChipView extends FrameLayout implements View.OnCreateContextMenuListener,MenuItem.OnMenuItemClickListener {
    private String str;
    public ChipView(Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.activity_frame, this);
    }

    public void displayItem(String text) {
        ((TextView)findViewById(R.id.textView)).setText(text);
        str = text;
        setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select The Action");
        contextMenu.add(0, view.getId(), 0, "Play");
        contextMenu.add(0, view.getId(), 0, "Follow");
        contextMenu.add(0, view.getId(), 0, "Rankings");

        contextMenu.getItem(0).setOnMenuItemClickListener(this);
        contextMenu.getItem(1).setOnMenuItemClickListener(this);
        contextMenu.getItem(2).setOnMenuItemClickListener(this);
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        if (menuItem.getTitle().equals("Play")) {
            Intent intent = new Intent(getContext(), Play.class);
            intent.putExtra("play_topic",str);
            getContext().startActivity(intent);
            ((Activity) getContext()).finish();
            return true;
        } else if (menuItem.getTitle().equals("Follow")) {
            if (Topics.arrayList.contains(str)){
                Toast.makeText(getContext(),"Its already being Followed",Toast.LENGTH_SHORT).show();
            } else {
                Topics.arrayList.add(str);
                Followed_Topic add_topic = new Followed_Topic();
                add_topic.setUsername(MainPage.string);
                add_topic.setFoll_top(str);

                TopicService topicService = ServiceBuilder.buildService(TopicService.class);
                Call<Followed_Topic> call1 = topicService.createfollowedtopic(add_topic);

                call1.enqueue(new Callback<Followed_Topic>() {
                    @Override
                    public void onResponse(Call<Followed_Topic> call, Response<Followed_Topic> response) {
                    }

                    @Override
                    public void onFailure(Call<Followed_Topic> call, Throwable t) {

                    }
                });
            }
            return true;
        } else if (menuItem.getTitle().equals("Rankings")) {

            Intent intent = new Intent(getContext(), RankList.class);
            intent.putExtra("play_topic",str);
            getContext().startActivity(intent);


            return true;
        } else
            return false;
    }
}
