package com.example.mahe.quiztopia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahe.quiztopia.models.Followed_Topic;
import com.example.mahe.quiztopia.models.Ranking;
import com.example.mahe.quiztopia.models.User;
import com.example.mahe.quiztopia.services.RankingService;
import com.example.mahe.quiztopia.services.ServiceBuilder;
import com.example.mahe.quiztopia.services.TopicService;
import com.example.mahe.quiztopia.services.UserService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Result extends Activity {

    private int exp,lvl;
    public static double perc;
    int rank1,rank2,rank3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.pb);
        final TextView textView = (TextView) findViewById(R.id.textView6);
        final TextView textView1 = (TextView) findViewById(R.id.textView7);
        final TextView textView2 = (TextView) findViewById(R.id.textView8);
        exp = MainPage.experience;
        lvl = MainPage.level;

        int prog = exp;
        final int count = getIntent().getIntExtra("count",0);
        final String play_topic = getIntent().getStringExtra("play_topic");

        textView1.setText("You have answered " + count+ "/"+ Play.m );
        perc = (double) count/Play.m;
        Log.d("perc", String.valueOf(perc * 100));

        prog = prog + count * 2;
        while ((prog)/(lvl * 15) >= 1 ) {
            prog = prog - lvl*15;
            lvl = lvl + 1;
        }
        exp = prog;
        progressBar.setProgress((exp * 100) / (lvl * 15));
        textView.setText("EXP = "+exp+"/"+(lvl*15));
        textView2.setText("LVL = "+lvl);

        Button home = (Button) findViewById(R.id.home_btn);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final RankingService rankingService = ServiceBuilder.buildService(RankingService.class);
                Call<ArrayList<Ranking>> rankingCall = rankingService.getrankings();

                rankingCall.enqueue(new Callback<ArrayList<Ranking>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Ranking>> call, Response<ArrayList<Ranking>> response) {
                        int num = response.body().size();
                        for (int i = 0 ; i < num ; i++) {
                            if(response.body().get(i).getUsername().equals(MainPage.string)) {
                                rank1 = response.body().get(i).getRank1();
                                rank2 = response.body().get(i).getRank2();
                                rank3 = response.body().get(i).getRank3();
                                break;
                            }
                        }

                        if (play_topic.equals("General Knowledge")) {
                            rank1 = rank1 + count;
                        } else if (play_topic.equals("Android")) {
                            rank2 = rank2 + count;
                        } else if (play_topic.equals("Basic Math")) {
                            rank3 = rank3 + count;
                        }

                        Call<Ranking> arrayListCall = rankingService.updaterankings(
                                MainPage.string,
                                rank1,
                                rank2,
                                rank3
                        );

                        arrayListCall.enqueue(new Callback<Ranking>() {
                            @Override
                            public void onResponse(Call<Ranking> call, Response<Ranking> response) {
                            }

                            @Override
                            public void onFailure(Call<Ranking> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Ranking>> call, Throwable t) {

                    }
                });

                Intent intent = new Intent(Result.this,MainPage.class);
                intent.putExtra("username",MainPage.string);
                intent.putExtra("lvl",lvl);
                intent.putExtra("exp",exp);
                intent.putExtra("ll",MainPage.ll);
                startActivity(intent);
                finish();

            }
        });

    }
}
