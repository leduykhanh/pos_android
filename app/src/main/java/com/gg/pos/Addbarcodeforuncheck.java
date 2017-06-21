package com.gg.pos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import java.util.UUID;

/**
 * Created by Go Goal on 5/30/2017.
 */

public class Addbarcodeforuncheck extends AppCompatActivity {


    EditText sed, bared;
    RelativeLayout close;
    Button create, genbtn;
    static Spinner sp;
    String itemid;
    AppCompatActivity ac;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_bar_alertdialog);

        ac = this;
        sed = (EditText) findViewById(R.id.sed);
        bared = (EditText) findViewById(R.id.bared);
        genbtn = (Button) findViewById(R.id.gened);


        close = (RelativeLayout) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create = (Button) findViewById(R.id.createbtn);


        itemid=getIntent().getExtras().getString("itemid");


        genbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID myuuid = UUID.randomUUID();
                long highbits = myuuid.getMostSignificantBits();
                String s=highbits+"";
                s=s.substring(1,11);
                bared.setText(s);
            }
        });



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sed.getText().length() < 1) {
                    Mytoast.show(getApplicationContext(), "Please Enter Serial No");
                } else {

                    networkholder.Addbarcodeandserial(sed.getText().toString(),bared.getText().toString(),itemid);
                    Addbarcode.showpg();
                    finish();

                }

            }
        });




    }
}
