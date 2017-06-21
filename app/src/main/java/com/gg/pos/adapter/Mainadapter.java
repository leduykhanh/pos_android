package com.gg.pos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.pos.Inventory;
import com.gg.pos.R;
import com.gg.pos.object.get;
import com.gg.pos.utility.HFRecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Go Goal on 4/19/2017.
 */
public class Mainadapter extends HFRecyclerView<get> {

    int screenWidth;
    ArrayList<get> list;
    Context context;

    public Mainadapter(ArrayList<get> ll, int widthPixels, Context context1) {
        super(ll, true, true);
        screenWidth = widthPixels;
        list = ll;
        context = context1;
    }

    public int getCount() {
        return list.size();
    }


    @Override
    protected RecyclerView.ViewHolder getItemView(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new holder(inflater.inflate(R.layout.mainrow, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getHeaderView(LayoutInflater inflater, ViewGroup parent) {
        return new HeaderViewHolder(inflater.inflate(R.layout.hrow, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder getFooterView(LayoutInflater inflater, ViewGroup parent) {
        return new FooterViewHolder(inflater.inflate(R.layout.hrow, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
       /* if (holder instanceof FooterViewHolder) {


            FooterViewHolder FooterViewHolder = (FooterViewHolder) holder;

            if (list.size() < 1) {
                FooterViewHolder.nettv.setVisibility(View.GONE);
                FooterViewHolder.taxtv.setVisibility(View.GONE);
                FooterViewHolder.myview.setVisibility(View.GONE);
            } else {
                FooterViewHolder.nettv.setVisibility(View.VISIBLE);
                FooterViewHolder.taxtv.setVisibility(View.VISIBLE);
                FooterViewHolder.myview.setVisibility(View.VISIBLE);
            }
            double ddd = 0;
            double total = 0;
            double gst = 0;
            for (int i = 0; i < list.size(); i++) {
                double iop;
                try {
                    iop = Double.parseDouble(list.get(i).getSellamount());
                } catch (Exception e) {
                    iop = 0;
                }

                total = total + iop;
                gst = total * 0.07;
                ddd = total + gst;


            }

            FooterViewHolder.nettv.setText(Html.fromHtml("Net Amount:     <font color='green'>$" + new DecimalFormat("##.##").format(ddd) + "</font>"));
            FooterViewHolder.taxtv.setText(Html.fromHtml("(Inclusive <font color='green'>7% GST $" + new DecimalFormat(".##").format(gst) + ")</font>"));


        } else*/ if (holder instanceof holder) {

            holder ItemViewHolder = (holder) holder;
            ItemViewHolder.textview[0].setText(list.get(position - 1).getSellitemis());
            ItemViewHolder.textview[1].setText(list.get(position - 1).getSellqty());
            ItemViewHolder.textview[2].setText("$" + list.get(position - 1).getSelling_price());
            ItemViewHolder.textview[3].setText("$" + list.get(position - 1).getSellamount());
        }


    }

    @Override
    public int getItemCount() {
        return list.size() + 2;
    }

    public void refresh(ArrayList<get> list1) {
        list = list1;
        notifyDataSetChanged();
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

      //  TextView nettv, taxtv;
//        View myview;

        public FooterViewHolder(View itemView) {
            super(itemView);
         /*   nettv = (TextView) itemView.findViewById(R.id.netamounttv);
            taxtv = (TextView) itemView.findViewById(R.id.tax);
            myview = (View) itemView.findViewById(R.id.myview);*/

        }
    }

    public class holder extends RecyclerView.ViewHolder {

        public RelativeLayout[] tv = new RelativeLayout[4];


        public TextView[] textview = new TextView[4];


        public holder(View itemView) {
            super(itemView);
            tv[0] = (RelativeLayout) itemView.findViewById(R.id.t1);
            tv[1] = (RelativeLayout) itemView.findViewById(R.id.t2);
            tv[2] = (RelativeLayout) itemView.findViewById(R.id.t3);
            tv[3] = (RelativeLayout) itemView.findViewById(R.id.t4);


            textview[0] = (TextView) itemView.findViewById(R.id.tv1);
            textview[1] = (TextView) itemView.findViewById(R.id.tv2);
            textview[2] = (TextView) itemView.findViewById(R.id.tv3);
            textview[3] = (TextView) itemView.findViewById(R.id.tv4);


        }
    }
}
