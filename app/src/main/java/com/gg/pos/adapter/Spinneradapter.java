package com.gg.pos.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gg.pos.R;

import java.util.ArrayList;

/**
 * Created by Go Goal on 2/13/2017.
 */

public class Spinneradapter extends ArrayAdapter {

    Context con;
    ArrayList<String> str;
    public Spinneradapter(Context context, ArrayList<String> str1) {
        super(context,0);
        con=context;
        str=str1;

    }

    @Override
    public int getCount() {
        return str.size();
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.one_text_row, parent,
                false);
        TextView make = (TextView) row.findViewById(R.id.one_text);

        make.setText(str.get(position));
        return row;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.one_text_row, parent,
                false);
        TextView make = (TextView) row.findViewById(R.id.one_text);

        make.setText(str.get(position));
        return row;
    }
}
