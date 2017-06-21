package com.gg.pos;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.gg.pos.adapter.Spinneradapter;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;

/**
 * Created by Go Goal on 5/30/2017.
 */

public class Addmodel extends AppCompatActivity {


    EditText ed;
    RelativeLayout close;
    Button create;
    static Spinner sp;
    AppCompatActivity ac;
    static ArrayList<String> list_id,list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addmodel);

        ac = this;
        ed = (EditText) findViewById(R.id.branded);
        sp = (Spinner) findViewById(R.id.spinner2);

        close = (RelativeLayout) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create = (Button) findViewById(R.id.createbtn);

        list_id=Model.getspinnerlist_id();
        list=Model.getspinnerlist();
        Spinneradapter adapteree = new Spinneradapter(ac, list);
        sp.setAdapter(adapteree);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed.getText().length() < 1) {
                    Mytoast.show(getApplicationContext(), "Please Enter Model Name");
                } else {

                    networkholder.addmodel(ed.getText().toString(), list_id.get(sp.getSelectedItemPosition()));
                    Model.showpg();
                    finish();

                }

            }
        });





    }
}
