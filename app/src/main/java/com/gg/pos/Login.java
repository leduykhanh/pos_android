package com.gg.pos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Jsonparser;
import com.gg.pos.utility.Mytoast;

/**
 * Created by Go Goal on 4/18/2017.
 */

public class Login extends AppCompatActivity {

   static EditText usered, passed;
    Button login;
    static AppCompatActivity ac;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ac = this;

        usered = (EditText) findViewById(R.id.user);
        passed = (EditText) findViewById(R.id.pass);

        login = (Button) findViewById(R.id.loginbtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edenable_distable(false);

                networkholder.Logincheck(usered.getText().toString(), passed.getText().toString());


            }
        });


    }

    private static void edenable_distable(boolean b) {
        usered.setEnabled(b);
        passed.setEnabled(b);
    }


    public static void Feedback_error() {
        edenable_distable(true);
        Mytoast.show(ac, "Network Fail");
    }

    public static void Feedback(String str) {

        String status= Jsonparser.getonestring(str,"status");
        String id= Jsonparser.getonestring(str,"id");
        String name= Jsonparser.getonestring(str,"username");
        String row= Jsonparser.getonestring(str,"row");
        String userid= Jsonparser.getonestring(str,"userid");

        if (status.equals("1")||status.equals("2")){
            Intent it=new Intent(ac,MainActivity.class);
            it.putExtra("status",status);
            it.putExtra("name",name);
            it.putExtra("row",row);
            ac.startActivity(it);
            ac.finish();


            SharedPreferences sharedpreferences = ac.getSharedPreferences("setting", ac.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putString("row", status);
            editor.putString("id", id);
            editor.putString("userid", userid);
            editor.commit();

        }else {
            edenable_distable(true);
            Mytoast.show(ac, "Invalid Login");
        }


    }
}
