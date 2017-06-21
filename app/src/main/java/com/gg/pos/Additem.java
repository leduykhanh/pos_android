package com.gg.pos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;

import com.gg.pos.adapter.Spinneradapter;
import com.gg.pos.adapter.Spinnerbrandadapter;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Go Goal on 5/30/2017.
 */

public class Additem extends AppCompatActivity {


    EditText desed, priceed, mined, serialed, baseed;
    RelativeLayout close;
    Button create, sergetnbtn;
    static Spinner sp1, sp2, sp3;
    AppCompatActivity ac;
    static ArrayList<get> brand, model,category;
    Switch serialcb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_insert);

        ac = this;

        close = (RelativeLayout) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create = (Button) findViewById(R.id.createbtn);

        brand = Inventory.getbrandlist();
        model = Inventory.getmodlelist();
        category= Inventory.getcategorylist();


        desed = (EditText) findViewById(R.id.desed);
        priceed = (EditText) findViewById(R.id.priceed);
        mined = (EditText) findViewById(R.id.mined);
        serialed = (EditText) findViewById(R.id.serialed);
        baseed = (EditText) findViewById(R.id.baseed);

        serialcb = (Switch) findViewById(R.id.snoch);
        sergetnbtn = (Button) findViewById(R.id.serialbtn);
        sergetnbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UUID myuuid = UUID.randomUUID();
                long highbits = myuuid.getMostSignificantBits();
                String s = highbits + "";
                s = s.substring(1, 11);
                serialed.setText(s);
            }
        });
        serialcb.setChecked(false);

        sergetnbtn.setEnabled(false);
        serialed.setEnabled(false);

        sp1 = (Spinner) findViewById(R.id.spinner1);
        sp2 = (Spinner) findViewById(R.id.spinner2);
        sp3 = (Spinner) findViewById(R.id.spinner3);


        Spinnerbrandadapter adapteree = new Spinnerbrandadapter(ac, brand);
        sp1.setAdapter(adapteree);

        Spinnerbrandadapter adapteree1 = new Spinnerbrandadapter(ac, model);
        sp2.setAdapter(adapteree1);

        Spinnerbrandadapter adapteree2 = new Spinnerbrandadapter(ac, category);
        sp3.setAdapter(adapteree2);

        serialcb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (serialcb.isChecked() == true) {

                    sergetnbtn.setEnabled(true);
                    serialed.setEnabled(true);

                } else {

                    sergetnbtn.setEnabled(false);
                    serialed.setEnabled(false);
                }
            }
        });


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                networkholder.additem(desed.getText().toString(), brand.get(sp1.getSelectedItemPosition()).getId(), model.get(sp2.getSelectedItemPosition()).getId(), category.get(sp3.getSelectedItemPosition()).getId(), priceed.getText().toString(), mined.getText().toString(), baseed.getText().toString(), serialed.getText().toString(), serialcb.isChecked());
                Model.showpg();
                finish();


            }
        });


    }
}
