package com.example.mahe.quiztopia;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;
import com.example.mahe.quiztopia.models.Ranking;
import com.example.mahe.quiztopia.services.RankingService;
import com.example.mahe.quiztopia.services.ServiceBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class RankList extends Activity {

    private TextView getTextView(int id, String title) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title);
        tv.setTextSize(18);
        tv.setTextColor(Color.parseColor("#323232"));
        tv.setBackgroundColor(Color.parseColor("#b3d1ff"));
        tv.setPadding(40, 40, 40, 40);
        tv.setLayoutParams(getLayoutParams());
        return tv;
    }

    private LayoutParams getLayoutParams() {
        TableRow.LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        params.weight = 1;
        return params;
    }

    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
    }

    private LinkedHashMap sortMapByValuesWithDuplicates(Map passedMap) {
        List mapKeys = new ArrayList(passedMap.keySet());
        List mapValues = new ArrayList(passedMap.values());
        Collections.sort(mapKeys,Collections.reverseOrder());
        Collections.sort(mapValues,Collections.reverseOrder());

        LinkedHashMap sortedMap = new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = passedMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)) {
                    passedMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put((String) key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_list);

        Bundle bundle = new Bundle();
        final String playtopic = getIntent().getStringExtra("play_topic");

        TextView textView = (TextView) findViewById(R.id.textView9);
        textView.setText(playtopic);



        final TableLayout tl = findViewById(R.id.tableLayoout);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "Rank"));
        tr.addView(getTextView(0, "Name"));
        tr.addView(getTextView(0, "NOA"));
        tl.addView(tr, getTblLayoutParams());

        final RankingService rankingService = ServiceBuilder.buildService(RankingService.class);
        Call<ArrayList<Ranking>> rankingCall = rankingService.getrankings();

        rankingCall.enqueue(new Callback<ArrayList<Ranking>>() {
            @Override
            public void onResponse(Call<ArrayList<Ranking>> call, Response<ArrayList<Ranking>> response) {
                Map<String, Integer> map = new HashMap<String, Integer>();

                int num = response.body().size();

                if(playtopic.equals("General Knowledge")) {
                    for (int i = 0 ; i < num ; i++) {
                        map.put(response.body().get(i).getUsername(),response.body().get(i).getRank1());
                    }
                } else if(playtopic.equals("Android")) {
                    for (int i = 0 ; i < num ; i++) {
                        map.put(response.body().get(i).getUsername(),response.body().get(i).getRank2());
                    }

                } else if(playtopic.equals("Basic Math")) {
                    for (int i = 0 ; i < num ; i++) {
                        map.put(response.body().get(i).getUsername(),response.body().get(i).getRank3());
                    }
                }

                Map<String,Integer> sorted = sortMapByValuesWithDuplicates(map);

                int i = 1;
                for (String name: sorted.keySet()){
                    if(!(sorted.get(name).toString().equals("0"))) {
                        TableRow tr1 = new TableRow(RankList.this);
                        tr1.setLayoutParams(getLayoutParams());
                        tr1.addView(getTextView(i, String.valueOf(i)));
                        tr1.addView(getTextView(i, name.toString()));
                        tr1.addView(getTextView(i, sorted.get(name).toString()));
                        tl.addView(tr1, getTblLayoutParams());
                        i++;
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Ranking>> call, Throwable t) {

            }
        });

    }
}

