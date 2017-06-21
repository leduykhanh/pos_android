package com.gg.pos;

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
import com.gg.pos.adapter.Spinnerbrandadapter;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;

/**
 * Created by Go Goal on 5/30/2017.
 */

public class Addsupplierinstock extends AppCompatActivity {


    EditText costed, costqty;
    RelativeLayout close;
    Button create;
    static Spinner sp;
    AppCompatActivity ac;
    static ArrayList<get> list;
    String itemid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_pdetail);

        ac = this;
        costed = (EditText) findViewById(R.id.costed);
        costqty = (EditText) findViewById(R.id.costqty);


        sp = (Spinner) findViewById(R.id.spinner2);

        close = (RelativeLayout) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create = (Button) findViewById(R.id.createbtn);
        itemid=getIntent().getExtras().getString("itemid");
        list = Inventory.getspinnerlist();
        Spinnerbrandadapter adapteree = new Spinnerbrandadapter(ac, list);
        sp.setAdapter(adapteree);


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (costed.getText().length() < 1 || costqty.getText().length() < 1) {
                    Mytoast.show(getApplicationContext(), "Please Enter Cost and Qty Name");
                } else {

                    networkholder.addqty(itemid, list.get(sp.getSelectedItemPosition()).getId(), costed.getText().toString(), costqty.getText().toString());
                    Inventory.showpg();
                    finish();

                }

            }
        });





    }
}
