package com.example.mahe.quiztopia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Handler;
import android.widget.Toast;

import com.example.mahe.quiztopia.models.PlayDetail;
import com.example.mahe.quiztopia.services.PlayService;
import com.example.mahe.quiztopia.services.ServiceBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Play extends Activity {

    public static int m;int n; String quest[]; String opt[][]; String ans[]; int i; int count = 0;
    TextView textView1; ListView listView;  boolean touched; Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        textView1 = (TextView) findViewById(R.id.textView4);
        listView = (ListView) findViewById(R.id.listView1);

        textView1.setVisibility(View.INVISIBLE);
        listView.setVisibility(View.INVISIBLE);

        Bundle bundle = getIntent().getExtras();
        final String play_topic = bundle.getString("play_topic");

        PlayService playService = ServiceBuilder.buildService(PlayService.class);
        if (play_topic.equals("General Knowledge")) {
            Call<ArrayList<PlayDetail>> call = playService.getplay1();

            call.enqueue(new Callback<ArrayList<PlayDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<PlayDetail>> call, Response<ArrayList<PlayDetail>> response) {
                    m = response.body().size();
                    quest = new String[m];
                    ans = new String[m];
                    n = response.body().get(0).getOpt().length;
                    opt = new String[m][n];
                    for (i = 0; i < m; i++) {
                        quest[i] = response.body().get(i).getQuest();
                        ans[i] = response.body().get(i).getAns();
                        for (int j = 0; j < n; j++) {
                            opt[i][j] = response.body().get(i).getOpt()[j];
                        }

                    }
                    i = 0;
                    touched = true;
                    t.start();

                }

                @Override
                public void onFailure(Call<ArrayList<PlayDetail>> call, Throwable t) {
                    Toast.makeText(Play.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            });
        } else if (play_topic.equals("Android")) {
            Call<ArrayList<PlayDetail>> call = playService.getplay2();

            call.enqueue(new Callback<ArrayList<PlayDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<PlayDetail>> call, Response<ArrayList<PlayDetail>> response) {
                    m = response.body().size();
                    quest = new String[m];
                    ans = new String[m];
                    n = response.body().get(0).getOpt().length;
                    opt = new String[m][n];
                    for (i = 0; i < m; i++) {
                        quest[i] = response.body().get(i).getQuest();
                        ans[i] = response.body().get(i).getAns();
                        for (int j = 0; j < n; j++) {
                            opt[i][j] = response.body().get(i).getOpt()[j];
                        }

                    }
                    i = 0;
                    touched = true;
                    t.start();

                }

                @Override
                public void onFailure(Call<ArrayList<PlayDetail>> call, Throwable t) {
                    Toast.makeText(Play.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            });
        } else if (play_topic.equals("Basic Math")) {
            Call<ArrayList<PlayDetail>> call = playService.getplay3();

            call.enqueue(new Callback<ArrayList<PlayDetail>>() {
                @Override
                public void onResponse(Call<ArrayList<PlayDetail>> call, Response<ArrayList<PlayDetail>> response) {
                    m = response.body().size();
                    quest = new String[m];
                    ans = new String[m];
                    n = response.body().get(0).getOpt().length;
                    opt = new String[m][n];
                    for (i = 0; i < m; i++) {
                        quest[i] = response.body().get(i).getQuest();
                        ans[i] = response.body().get(i).getAns();
                        for (int j = 0; j < n; j++) {
                            opt[i][j] = response.body().get(i).getOpt()[j];
                        }

                    }
                    i = 0;
                    touched = true;
                    t.start();

                }

                @Override
                public void onFailure(Call<ArrayList<PlayDetail>> call, Throwable t) {
                    Toast.makeText(Play.this, "Failed", Toast.LENGTH_SHORT).show();
                }

            });
        }

        t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                touched = false;
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                                        if (!touched) {
                                            touched = true;
                                            if (adapterView.getItemAtPosition(pos).toString().equals(ans[i])) {
                                                adapterView.getChildAt(pos).
                                                        setBackgroundColor(Color.parseColor("#52be80"));

                                                count++;
                                            } else {
                                                adapterView.getChildAt(pos).
                                                        setBackgroundColor(Color.parseColor("#e74c3c"));
                                                Toast.makeText(Play.this, ans[i],
                                                        Toast.LENGTH_SHORT).show();
                                                ((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(150);
                                            }

                                        }
                                    }
                                });
                            }
                        });

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView1.setVisibility(View.INVISIBLE);
                                listView.setVisibility(View.INVISIBLE);
                            }
                        });
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView1.setText(quest[i]);
                                textView1.setVisibility(View.VISIBLE);
                            }
                        });
                        Thread.sleep(2000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                listView.setAdapter(new ArrayAdapter<>(Play.this,
                                        R.layout.activity_textview, R.id.textView5, opt[i]));
                                listView.setVisibility(View.VISIBLE);
                            }
                        });
                        Thread.sleep(4000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                i++;
                            }
                        });
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (i >= m) {
                                    Toast.makeText(Play.this, "No. of Correct ans : " + count, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Play.this, Result.class);
                                    intent.putExtra("count", count);
                                    intent.putExtra("play_topic", play_topic);
                                    startActivity(intent);
                                    finish();
                                    t.interrupt();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };


    }

}
