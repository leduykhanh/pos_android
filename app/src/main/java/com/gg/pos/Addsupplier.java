package com.gg.pos;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Mytoast;

/**
 * Created by Go Goal on 5/30/2017.
 */

public class Addsupplier extends Activity {


    EditText cname, caddress, sname, sdes, sph, semail;
    RelativeLayout close;
    Button create;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addsuppiler);

        cname = (EditText) findViewById(R.id.cname);
        caddress = (EditText) findViewById(R.id.caddress);
        sname = (EditText) findViewById(R.id.sname);
        sdes = (EditText) findViewById(R.id.sdes);
        sph = (EditText) findViewById(R.id.sph);
        semail = (EditText) findViewById(R.id.semail);

        close = (RelativeLayout) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create = (Button) findViewById(R.id.createbtn);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                networkholder.addsuppiler(cname.getText().toString(), caddress.getText().toString(), sname.getText().toString(), sdes.getText().toString(), sph.getText().toString(), semail.getText().toString());
                Suppiler.showpg();
                finish();

            }
        });


    }
}
