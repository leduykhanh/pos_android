package com.gg.pos.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.pos.R;
import com.gg.pos.object.get;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/20/2017.
 */
public class Suppileradapter extends RecyclerView.Adapter<Suppileradapter.holder> {

    ArrayList<get> list;
    int screenWidth;

    public Suppileradapter(ArrayList<get> list1, int screenWidth1) {

        list = list1;
        screenWidth=screenWidth1;
    }

    @Override
    public Suppileradapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Suppileradapter.holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sprow, parent, false));
    }

    @Override
    public void onBindViewHolder(holder holder, int position) {

        int row=position+1;
        holder.tvv[0].setText(row+"");
        holder.tvv[1].setText(list.get(position).getCname());
        holder.tvv[2].setText(list.get(position).getCaddress());
        holder.tvv[3].setText(list.get(position).getSname());
        holder.tvv[4].setText(list.get(position).getSdes());
        holder.tvv[5].setText(list.get(position).getSph());
        holder.tvv[6].setText(list.get(position).getSemail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void refresh(ArrayList<get> list11) {
        list = list11;
        notifyDataSetChanged();
    }

    public class holder extends RecyclerView.ViewHolder {


        RelativeLayout[] tv=new RelativeLayout[6];
        RelativeLayout tv3;


        public TextView [] tvv=new TextView[7];

        public holder(View itemView) {
            super(itemView);

            tv[0]= (RelativeLayout) itemView.findViewById(R.id.t1);
            tv[1]= (RelativeLayout) itemView.findViewById(R.id.t2);
            tv[2]= (RelativeLayout) itemView.findViewById(R.id.t4);
            tv[3]= (RelativeLayout) itemView.findViewById(R.id.t5);
            tv[4]= (RelativeLayout) itemView.findViewById(R.id.t6);
            tv[5]= (RelativeLayout) itemView.findViewById(R.id.t7);
            tv3= (RelativeLayout) itemView.findViewById(R.id.t3);

            tvv[0]= (TextView) itemView.findViewById(R.id.tvv1);
            tvv[1]= (TextView) itemView.findViewById(R.id.tvv2);
            tvv[2]= (TextView) itemView.findViewById(R.id.tvv3);
            tvv[3]= (TextView) itemView.findViewById(R.id.tvv4);
            tvv[4]= (TextView) itemView.findViewById(R.id.tvv5);
            tvv[5]= (TextView) itemView.findViewById(R.id.tvv6);
            tvv[6]= (TextView) itemView.findViewById(R.id.tvv7);



        }
    }
}