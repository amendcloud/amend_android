package com.tactlab.cloudamendsample.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tactlab.cloudamendsample.R;
import com.tactlab.cloudamendsample.activity.MyAmendFunction;

import java.util.List;


public class AmendSubOptionsAdapter extends BaseAdapter {
    private final List<String> items;
    private LayoutInflater inflater;
    private Context con;


    public AmendSubOptionsAdapter(Context con, List<String> items) {
        this.con = con;
        this.items = items;
        inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(final int position, View cv, ViewGroup parent) {
        if (cv == null) {
            cv = inflater.inflate(R.layout.amendsubviewadapter, null);
        }

        TextView tvOptions = (TextView) cv.findViewById(R.id.tvOptions);
        tvOptions.setText(items.get(position).toString());


        LinearLayout llParent = (LinearLayout) cv.findViewById(R.id.llParent);
        llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(con, MyAmendFunction.class);
                i.putExtra("FUNCTION_NAME", items.get(position).toString());
                con.startActivity(i);
            }
        });

        return cv;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}