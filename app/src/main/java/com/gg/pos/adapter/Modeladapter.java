package com.gg.pos.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gg.pos.Brand;
import com.gg.pos.Model;
import com.gg.pos.R;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/19/2017.
 */
public class Modeladapter extends RecyclerView.Adapter<Modeladapter.holder> {

    ArrayList<get> list;
    AppCompatActivity ac;

    public Modeladapter(AppCompatActivity ac1, ArrayList<get> list1) {

        list = list1;
        ac=ac1;
    }

    @Override
    public Modeladapter.holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.modelrow, parent, false));
    }

    @Override
    public void onBindViewHolder(Modeladapter.holder holder, final int position) {

        int row=position+1;
        holder.t1.setText(row + ".   " + list.get(position).getModelname());
        holder.t2.setText(list.get(position).getBrandname());
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder ab=new AlertDialog.Builder(ac);
                ab.setTitle("Delete Model");
                ab.setMessage("Are You Sure To Delete This Model");
                ab.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        networkholder.deletemodel(list.get(position).getModelid());
                        Model.showpg();

                    }
                }).setNegativeButton("Cancel",null).show();



            }
        });

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


        TextView t1,t2;
        ImageView iv;

        public holder(View itemView) {
            super(itemView);

            t1 = (TextView) itemView.findViewById(R.id.t1);
            t2 = (TextView) itemView.findViewById(R.id.t2);
            iv= (ImageView) itemView.findViewById(R.id.ivv);


        }
    }
}
