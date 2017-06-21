package com.gg.pos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.gg.pos.adapter.Itemadapter;
import com.gg.pos.adapter.userdetailadapter;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Jsonparser;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/22/2017.
 */

public class Userdetail extends AppCompatActivity {

    RelativeLayout[] tv = new RelativeLayout[5];
    static AppCompatActivity ac;
    static ProgressBar pg;
    static SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView rv;
    static userdetailadapter adapter;

    static EditText user, pass, repass, fullname, email, address, phone;
    static RadioButton rdadmin, rduser;
    static Button create;
    static Dialog abcre;

    TextView createuserbtn;

    LinearLayout navopener;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlayout);




        tv[0] = (RelativeLayout) findViewById(R.id.t1);
        tv[1] = (RelativeLayout) findViewById(R.id.t2);
        tv[2] = (RelativeLayout) findViewById(R.id.t3);
        tv[3] = (RelativeLayout) findViewById(R.id.t4);
        tv[4] = (RelativeLayout) findViewById(R.id.t5);

        navopener= (LinearLayout) findViewById(R.id.navopener);
        navopener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ac = this;
        rv = (RecyclerView) findViewById(R.id.rv);
        pg = (ProgressBar) findViewById(R.id.progressBar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);



        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);


        adapter = new userdetailadapter(new ArrayList<get>(), 0);
        rv.setAdapter(adapter);



        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkholder.getuser();
            }
        });

        networkholder.getuser();


        createuserbtn= (TextView) findViewById(R.id.creatuserbtn);
        createuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(getApplicationContext(),Createuser.class);
                startActivity(it);
                overridePendingTransition(0, 0);
            }
        });




    }


    public static void Feedback(String s) {

        mSwipeRefreshLayout.setRefreshing(false);
        pg.setVisibility(View.GONE);
        ArrayList<get> list=new ArrayList<>();
        list= Jsonparser.getlist_user(s);
        adapter.refresh(list);
        abcre.dismiss();
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



    public static void Feedback_error() {

        Enable_disable(true);
        Mytoast.show(ac, "Network Fail");

    }

    public static void Feedback_userlist(String s) {

        mSwipeRefreshLayout.setRefreshing(false);
        pg.setVisibility(View.GONE);
        ArrayList<get> list=new ArrayList<>();
        list= Jsonparser.getlist_user(s);
        adapter.refresh(list);

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
}
