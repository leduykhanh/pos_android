package com.gg.pos;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Mytoast;

/**
 * Created by Go Goal on 4/18/2017.
 */
public class Createuser extends Activity{


    static EditText user,pass,repass,fullname,email,address,phone;
    static Button create, rdadmin,rduser;
    static Activity ac;
    RelativeLayout close;
    boolean usertype=false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createuser);


        ac=this;
        user= (EditText) findViewById(R.id.user);
        pass= (EditText) findViewById(R.id.pass);
        repass= (EditText) findViewById(R.id.repass);
        fullname= (EditText) findViewById(R.id.fullname);
        email= (EditText) findViewById(R.id.email);
        address= (EditText) findViewById(R.id.address);
        phone= (EditText) findViewById(R.id.phone);
        create= (Button) findViewById(R.id.createbtn);
        rdadmin= (Button) findViewById(R.id.adminrd);
        rduser= (Button) findViewById(R.id.userrd);
        close= (RelativeLayout) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        rdadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rdadmin.setBackgroundResource(R.drawable.rectangle_round_browm);
                rdadmin.setEnabled(false);
                rduser.setTextColor(getResources().getColor(R.color.bowntext));


                rduser.setBackgroundResource(R.drawable.rectangle_round_green);
                rduser.setEnabled(true);
                rduser.setTextColor(getResources().getColor(R.color.realwhite));

                usertype=true;

            }
        });


        rduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rduser.setBackgroundResource(R.drawable.rectangle_round_browm);
                rduser.setEnabled(false);
                rduser.setTextColor(getResources().getColor(R.color.bowntext));

                rdadmin.setBackgroundResource(R.drawable.rectangle_round_red);
                rdadmin.setEnabled(true);
                rdadmin.setTextColor(getResources().getColor(R.color.realwhite));

                usertype=false;
            }
        });





        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = getinfo();
                if (status){
                    if (pass.getText().toString().equals(repass.getText().toString())){

                        String str="Are you sure to create this user as Employee?";
                        if (usertype){
                            str="Are you sure to create this user as ADMIN?";
                        }


                        AlertDialog.Builder ab=new AlertDialog.Builder(Createuser.this);
                        ab.setTitle("Alert!");
                        ab.setMessage(str);
                        ab.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Enable_disable(false);
                                String str="EMPLOYEE";
                                if (usertype){
                                    str="ADMIN";
                                }

                                networkholder.createuser(user.getText().toString(),pass.getText().toString(),fullname.getText().toString(),email.getText().toString(),address.getText().toString(),phone.getText().toString(),str);
                                finish();

                            }
                        }).setNegativeButton("Cancel",null).show();


                    }else{
                        Mytoast.show(getApplicationContext(),"Password not match");
                    }

                }
            }
        });

    }



    public boolean getinfo() {

        boolean info=true;

        if (user.getText().length()<1){
            info=false;
            Mytoast.show(getApplicationContext(),"Please Enter user name");

        }else if (pass.getText().length()<1) {
            info=false;
            Mytoast.show(getApplicationContext(),"Please Enter Password");
        }else if (repass.getText().length()<1) {
            info=false;
            Mytoast.show(getApplicationContext(),"Please Retype Password");
        }else if (fullname.getText().length()<1) {
            info=false;
            Mytoast.show(getApplicationContext(),"Please Enter Full name");

        }else if (email.getText().length()<1) {
            info=false;
            Mytoast.show(getApplicationContext(),"Please Enter Email");
        }else if (address.getText().length()<1) {
            info=false;
            Mytoast.show(getApplicationContext(),"Please Enter Address");
        }else if (phone.getText().length()<1) {
            info=false;
            Mytoast.show(getApplicationContext(),"Please Enter Contact No");
        }

        return info;
    }

    public static void Feedback(String s) {
        Enable_disable(true);
        Mytoast.show(ac, "User Create Successful");
        user.setText("");
        pass.setText("");
        repass.setText("");
        fullname.setText("");
        email.setText("");
        address.setText("");
        phone.setText("");

    }

    public static void Feedback_error() {
        Enable_disable(true);
        Mytoast.show(ac, "Network Fail");
    }

    private static void Enable_disable(boolean b) {
        user.setEnabled(b);
        pass.setEnabled(b);
        repass.setEnabled(b);
        fullname.setEnabled(b);
        email.setEnabled(b);
        address.setEnabled(b);
        phone.setEnabled(b);
        rdadmin.setEnabled(b);
        rduser.setEnabled(b);
        create.setEnabled(b);
    }
}
