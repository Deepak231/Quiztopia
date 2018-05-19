package com.example.mahe.quiztopia.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MAHE on 3/2/2018.
 */

public class ChipsAdapter extends RecyclerView.Adapter {
    private String[] chipsArray;

    public ChipsAdapter(String[] chipsArray) {
        this.chipsArray = chipsArray;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipViewHolder(new ChipView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ChipView)holder.itemView).displayItem(chipsArray[position]);
    }

    @Override
    public int getItemCount() {
        return chipsArray.length;
    }

    private class ChipViewHolder extends RecyclerView.ViewHolder {

        public ChipViewHolder(View itemView) {
            super(itemView);
        }
    }
}
