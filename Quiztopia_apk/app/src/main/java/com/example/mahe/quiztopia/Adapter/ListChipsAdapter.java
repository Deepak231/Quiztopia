package com.example.mahe.quiztopia.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mahe.quiztopia.R;

import java.util.ArrayList;

/**
 * Created by MAHE on 3/2/2018.
 */

public class ListChipsAdapter extends Adapter {

    private TextView textView;
    private ArrayList<String[]> chipsArray; private ArrayList<String> chipstitle;

    public ListChipsAdapter(ArrayList<String[]> chipsArray,ArrayList<String> chipstitle) {
        this.chipsArray = chipsArray; this.chipstitle = chipstitle;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipsViewHolder(new RowChipsView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        textView.setText(chipstitle.get(position));
        ((RowChipsView)holder.itemView).setAdapter(new ChipsAdapter(chipsArray.get(position)));
    }
    @Override
    public int getItemCount() {
        return chipsArray.size();
    }
    private class ChipsViewHolder extends RecyclerView.ViewHolder {

        public ChipsViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView2);
            textView.setTextColor(Color.parseColor("#015a85"));
        }
    }
}
