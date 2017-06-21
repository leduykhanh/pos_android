package com.gg.pos.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.print.PrintAttributes;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.pos.Addbarcode;
import com.gg.pos.Inventory;
import com.gg.pos.R;
import com.gg.pos.object.get;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/19/2017.
 */
public class Addbarcodeadapter extends RecyclerView.Adapter<Addbarcodeadapter.holder> {

    private Context contexts;
    int screenWidth;
    ArrayList<get> list;

    public Addbarcodeadapter(ArrayList<get> ll, int widthPixels, Context context) {

        screenWidth = widthPixels;
        list = ll;
    }

    @Override
    public Addbarcodeadapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_row, parent, false));
    }

    @Override
    public void onBindViewHolder(Addbarcodeadapter.holder holder, final int position) {


        holder.textview[0].setText(list.get(position).getSerial());
        holder.textview[1].setText(list.get(position).getBarcode());


        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Addbarcode.printclass(list.get(position).getBarcode());

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




        TextView[] textview = new TextView[2];
        ImageView iv;

        public holder(View itemView) {
            super(itemView);
            contexts=itemView.getContext();



            textview[0] = (TextView) itemView.findViewById(R.id.tv1);
            textview[1] = (TextView) itemView.findViewById(R.id.tv2);


            iv = (ImageView) itemView.findViewById(R.id.iv1);






        }
    }
}
