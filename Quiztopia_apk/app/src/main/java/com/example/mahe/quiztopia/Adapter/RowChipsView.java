package com.example.mahe.quiztopia.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.example.mahe.quiztopia.R;

/**
 * Created by MAHE on 3/2/2018.
 */

public class RowChipsView extends FrameLayout {
    public RowChipsView(@NonNull Context context) {
        super(context);
        initializeView(context);
    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.activity_recycler_horizontal, this);
        ((RecyclerView)findViewById(R.id.recyclerViewHorizontal)).setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    public void setAdapter(ChipsAdapter adapter) {
        ((RecyclerView)findViewById(R.id.recyclerViewHorizontal)).setAdapter(adapter);
    }
}
