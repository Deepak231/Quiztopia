package com.example.mahe.quiztopia;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.quiztopia.Adapter.ChipView;
import com.example.mahe.quiztopia.Adapter.FollowTopicAdapter;
import com.example.mahe.quiztopia.models.Followed_Topic;
import com.example.mahe.quiztopia.models.User;
import com.example.mahe.quiztopia.services.ServiceBuilder;
import com.example.mahe.quiztopia.services.TopicService;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Topics extends Fragment{

    public static ArrayList<String> arrayList = new ArrayList<>();
    GridView gridView; int n1 = 0;
    ArrayList<Followed_Topic> store = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        TopicService topicService = ServiceBuilder.buildService(TopicService.class);
        Call<ArrayList<Followed_Topic>> call = topicService.getfollowedtopic();

        call.enqueue(new Callback<ArrayList<Followed_Topic>>() {
            @Override
            public void onResponse(Call<ArrayList<Followed_Topic>> call, Response<ArrayList<Followed_Topic>> response) {
                int num = response.body().size();
                store = response.body();
                for (int i = 0 ; i < num ; i++) {
                    if(response.body().get(i).getUsername().equals(MainPage.string)) {
                        if(!(arrayList.contains(response.body().get(i).getFoll_top()))) {
                            arrayList.add(response.body().get(i).getFoll_top());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Followed_Topic>> call, Throwable t) {

            }
        });
        n1 = arrayList.size();
        return inflater.inflate(R.layout.activity_topics,container,false);
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        if (menuVisible) {
            TopicService topicService = ServiceBuilder.buildService(TopicService.class);
            Call<ArrayList<Followed_Topic>> call = topicService.getfollowedtopic();

            call.enqueue(new Callback<ArrayList<Followed_Topic>>() {
                @Override
                public void onResponse(Call<ArrayList<Followed_Topic>> call, Response<ArrayList<Followed_Topic>> response) {
                    int num = response.body().size();
                    store = response.body();
                    for (int i = 0 ; i < num ; i++) {
                        if(response.body().get(i).getUsername().equals(MainPage.string)) {
                            store = response.body();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<Followed_Topic>> call, Throwable t) {

                }
            });
            int num = arrayList.size();
            String follow_topics[] = new String[num];
            for (int i = 0; i < num ; i++) {
                follow_topics[i] = arrayList.get(i);

            }
            gridView.setAdapter(new FollowTopicAdapter(getContext(),follow_topics));
            this.registerForContextMenu(gridView);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.setHeaderTitle("Select The Action");
        contextMenu.add(0, view.getId(), 0, "Play");
        contextMenu.add(0, view.getId(), 0, "Unfollow");
        contextMenu.add(0, view.getId(), 0, "Rankings");
        AdapterView.AdapterContextMenuInfo aMenuInfo = (AdapterView.AdapterContextMenuInfo) contextMenuInfo;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        TextView textView = (TextView) adapterContextMenuInfo.targetView;
        final String string = (String) textView.getText();
        if (item.getTitle().equals("Play")) {
            Intent intent = new Intent(getActivity(),Play.class);
            intent.putExtra("play_topic",string);
            startActivity(intent);
            getActivity().finish();
            return true;
        }else if (item.getTitle().equals("Unfollow")) {
            int id = 0;
            for (int j = 0; j < store.size(); j++) {
                if (store.get(j).getUsername().equals(MainPage.string) &&
                        store.get(j).getFoll_top().equals(string)) {
                    id = store.get(j).getId();
                    break;
                }
            }

            TopicService topicService = ServiceBuilder.buildService(TopicService.class);
            Call<Void> deleterequest = topicService.deletetopic(id);

            deleterequest.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    arrayList.remove(string);
                    setMenuVisibility(true);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(getActivity(),"Try again",Toast.LENGTH_SHORT).show();
                }
            });

            return true;
        }else if (item.getTitle().equals("Rankings")) {
            Intent intent = new Intent(getActivity(),RankList.class);
            intent.putExtra("play_topic",string);
            startActivity(intent);

            return true;
        } else
            return super.onContextItemSelected(item);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = (GridView) view.findViewById(R.id.gridView1);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMenuVisibility(false);
    }

}
