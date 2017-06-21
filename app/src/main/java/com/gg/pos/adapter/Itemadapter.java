package com.gg.pos.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.pos.Inventory;
import com.gg.pos.R;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/19/2017.
 */
public class Itemadapter extends RecyclerView.Adapter<Itemadapter.holder> {

    int screenWidth;
    ArrayList<get> list;
    Context context;
    public Itemadapter(ArrayList<get> ll, int widthPixels) {

        screenWidth = widthPixels;
        list = ll;
    }

    @Override
    public Itemadapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false));
    }

    @Override
    public void onBindViewHolder(Itemadapter.holder holder, final int position) {

        int row = position + 1;
        holder.textview[0].setText(row+"");
        holder.textview[1].setText(list.get(position).getDescription());
        holder.textview[2].setText(list.get(position).getBrandname());
        holder.textview[3].setText(list.get(position).getModelname());
        holder.textview[4].setText(list.get(position).getCategory_name());
        holder.textview[5].setText(list.get(position).getSelling_price());
        holder.textview[6].setText(list.get(position).getTotal_quantity());

        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Inventory._purchasedetail(list.get(position).getItemid());

            }
        });


        if (list.get(position).getCheck().equals("true")){

            holder.iv2.setImageResource(R.drawable.ic_print);
        }else{
            holder.iv2.setImageResource(R.drawable.ic_add);

        }

        holder.iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.get(position).getCheck().equals("true")){

                    Inventory.gettoprintclass(list.get(position).getBarcode());


                }else {
                    Inventory.gonextbarcode(list.get(position).getItemid(), list.get(position).getCheck());
                }
            }
        });


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


        TextView[] textview = new TextView[7];
        ImageView iv2;
        Button iv;

        public holder(View itemView) {
            super(itemView);
            context=itemView.getContext();


            textview[0] = (TextView) itemView.findViewById(R.id.tv1);
            textview[1] = (TextView) itemView.findViewById(R.id.tv2);
            textview[2] = (TextView) itemView.findViewById(R.id.tv3);
            textview[3] = (TextView) itemView.findViewById(R.id.tv4);
            textview[4] = (TextView) itemView.findViewById(R.id.tv5);
            textview[5] = (TextView) itemView.findViewById(R.id.tv6);
            textview[6] = (TextView) itemView.findViewById(R.id.tv7);
            iv = (Button) itemView.findViewById(R.id.iv1);
            iv2 = (ImageView) itemView.findViewById(R.id.iv2);



        }
    }
}
