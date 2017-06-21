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

public class Addband extends Activity {


    EditText ed;
    RelativeLayout close;
    Button create;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbrand);

        ed = (EditText) findViewById(R.id.branded);

        close= (RelativeLayout) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        create= (Button) findViewById(R.id.createbtn);



        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ed.getText().length()<1){
                    Mytoast.show(getApplicationContext(), "Please Enter Brand Name");
                }else{

                    networkholder.addbrand(ed.getText().toString());
                    Brand.showpg();
                    finish();

                }

            }
        });



    }
}
