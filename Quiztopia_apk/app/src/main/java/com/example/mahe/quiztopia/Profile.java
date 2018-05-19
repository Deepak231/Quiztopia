package com.example.mahe.quiztopia;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.mahe.quiztopia.models.Ranking;
import com.example.mahe.quiztopia.models.User;
import com.example.mahe.quiztopia.services.RankingService;
import com.example.mahe.quiztopia.services.ServiceBuilder;
import com.example.mahe.quiztopia.services.UserService;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends android.support.v4.app.Fragment{

    String topic[] = {"General Knowledge","Android","Basic Math"};
    Integer rank[] = new Integer[3];
    PieChart pieChart; int lvl;
    TextView level101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        UserService userService = ServiceBuilder.buildService(UserService.class);
        Call<ArrayList<User>> call = userService.getuser();

        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                int num = response.body().size();
                for (int i = 0 ; i < num ; i++) {
                    if(response.body().get(i).getUsername().equals(MainPage.string)) {
                        lvl = response.body().get(i).getLvl();
                        break;
                    }
                }
                level101.setText("LVL : " + lvl);
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });

        final RankingService rankingService = ServiceBuilder.buildService(RankingService.class);
        Call<ArrayList<Ranking>> rankingCall = rankingService.getrankings();

        rankingCall.enqueue(new Callback<ArrayList<Ranking>>() {
            @Override
            public void onResponse(Call<ArrayList<Ranking>> call, Response<ArrayList<Ranking>> response) {
                int num = response.body().size();
                for (int i = 0 ; i < num ; i++) {
                    if(response.body().get(i).getUsername().equals(MainPage.string)) {
                        rank[0] = response.body().get(i).getRank1();
                        rank[1] = response.body().get(i).getRank2();
                        rank[2] = response.body().get(i).getRank3();
                        break;
                    }
                }
                List<PieEntry> list = new ArrayList<PieEntry>();
                for (int i = 0; i < topic.length; i++) {
                    list.add(new PieEntry(rank[i],topic[i]));
                }

                PieDataSet pieDataSet = new PieDataSet(list,"");
                pieDataSet.setValueTextSize(15);
                pieDataSet.setValueTextColor(Color.rgb(50,50,50));
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                PieData pieData = new PieData(pieDataSet);
                pieChart.setData(pieData);
                pieChart.invalidate();


            }

            @Override
            public void onFailure(Call<ArrayList<Ranking>> call, Throwable t) {

            }
        });



        return inflater.inflate(R.layout.activity_profile,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Toolbar toolbar =  view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.profile_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().equals("Sign Out") ) {
                    Intent intent = new Intent(getActivity(),Login.class);
                    startActivity(intent);
                    getActivity().finish();
                    return true;
                }else {
                    return false;
                }
            }
        });

        TextView profile_name = (TextView) view.findViewById(R.id.profile_name);

        profile_name.setText(MainPage.string);

        level101 = (TextView) view.findViewById(R.id.level101);


        profile_name.setText(MainPage.string);
        TextView last_act = (TextView) view.findViewById(R.id.last_act);
        String act = Login.ll_1.substring(0, Login.ll_1.contains(" ") ? Login.ll_1.indexOf(" ") : Login.ll_1.length());
        last_act.setText("Last Active : " + act);

        pieChart = (PieChart) view.findViewById(R.id.pie_chart);

    }


}
