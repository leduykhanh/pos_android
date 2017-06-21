package com.gg.pos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.pos.Inventory;
import com.gg.pos.R;
import com.gg.pos.object.get;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/19/2017.
 */
public class userdetailadapter extends RecyclerView.Adapter<userdetailadapter.holder> {

    int screenWidth;
    ArrayList<get> list;

    public userdetailadapter(ArrayList<get> ll, int widthPixels) {

        screenWidth = widthPixels;
        list = ll;
    }

    @Override
    public userdetailadapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.userrow, parent, false));
    }

    @Override
    public void onBindViewHolder(userdetailadapter.holder holder, final int position) {

        int row = position + 1;
        holder.textview[0].setText(row+"");
        holder.textview[1].setText(list.get(position).getUserid());
        holder.textview[2].setText(list.get(position).getUsername());
        holder.textview[3].setText(list.get(position).getUserstatus());
        holder.textview[4].setText(list.get(position).getUserfullname());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refresh(ArrayList<get> list1) {
        list = list1;
        notifyDataSetChanged();
    }

    public class holder extends RecyclerView.ViewHolder {



        TextView[] textview = new TextView[5];


        public holder(View itemView) {
            super(itemView);

            textview[0] = (TextView) itemView.findViewById(R.id.tv1);
            textview[1] = (TextView) itemView.findViewById(R.id.tv2);
            textview[2] = (TextView) itemView.findViewById(R.id.tv3);
            textview[3] = (TextView) itemView.findViewById(R.id.tv4);
            textview[4] = (TextView) itemView.findViewById(R.id.tv5);





        }
    }
}
