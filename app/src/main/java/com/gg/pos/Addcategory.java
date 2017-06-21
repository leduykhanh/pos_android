package com.gg.pos;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Mytoast;

/**
 * Created by Go Goal on 5/30/2017.
 */

public class Addcategory extends Activity {


    EditText ed;
    RelativeLayout close;
    Button create;
    TextView alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbrand);

        ed = (EditText) findViewById(R.id.branded);
        alert= (TextView) findViewById(R.id.alertTitle);

        alert.setText("Add Category");
        ed.setHint("ENTER CATEGORY NAME");

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
                    Mytoast.show(getApplicationContext(), "Please Enter Category Name");
                }else{

                    networkholder.addcartegory(ed.getText().toString());
                    Category.showpg();
                    finish();

                }

            }
        });



    }
}
