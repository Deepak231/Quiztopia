package com.example.mahe.quiztopia.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.example.mahe.quiztopia.R;
import com.example.mahe.quiztopia.Topics;

/**
 * Created by MAHE on 3/4/2018.
 */

public class FollowTopicAdapter extends BaseAdapter {

    private Context context;
    private final String[] textViewValues;

    public FollowTopicAdapter(Context context, String[] textViewValues) {
        this.context = context;
        this.textViewValues = textViewValues;
    }
    @Override
    public int getCount() {
        return textViewValues.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TextView textView;
        if(view == null)
        {
            textView = new TextView(context);
            textView.setTextColor(Color.parseColor("#ffffff"));
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new GridView.LayoutParams(400,300));
            textView.setBackgroundResource(R.drawable.shapes);
        }
        else
        {
            textView = (TextView) view;
        }
        textView.setText(textViewValues[i]);

        return textView;
    }


}
