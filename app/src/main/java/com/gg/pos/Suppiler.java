package com.gg.pos;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.pos.adapter.Suppileradapter;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Jsonparser;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/20/2017.
 */
public class Suppiler extends AppCompatActivity{

    RelativeLayout[] tv=new RelativeLayout[6];
    RelativeLayout tv3;
    static  AppCompatActivity ac;
    static ProgressBar pg;
    static SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView rv;
    static Suppileradapter adapter;

    LinearLayout navopener;
    TextView createuserbtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suppiler);
        navopener= (LinearLayout) findViewById(R.id.navopener);
        createuserbtn= (TextView) findViewById(R.id.creatuserbtn);

        navopener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        tv[0]= (RelativeLayout) findViewById(R.id.t1);
        tv[1]= (RelativeLayout) findViewById(R.id.t2);
        tv[2]= (RelativeLayout) findViewById(R.id.t4);
        tv[3]= (RelativeLayout) findViewById(R.id.t5);
        tv[4]= (RelativeLayout) findViewById(R.id.t6);
        tv[5]= (RelativeLayout) findViewById(R.id.t7);
        tv3= (RelativeLayout) findViewById(R.id.t3);
        ac=this;
        rv= (RecyclerView) findViewById(R.id.rv);
        pg= (ProgressBar) findViewById(R.id.progressBar);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);



        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        adapter=new Suppileradapter(new ArrayList<get>(),0);
        rv.setAdapter(adapter);



        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkholder.getsuppilerlist();
            }
        });

        networkholder.getsuppilerlist();



        createuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it=new Intent(getApplicationContext(),Addsupplier.class);
                startActivity(it);
                overridePendingTransition(0, 0);

            }
        });


    }



    public static void Feedback_error() {
        Mytoast.show(ac, "Network Fail");
        pg.setVisibility(View.GONE);
        mSwipeRefreshLayout.setRefreshing(false);
    }

    public static void Feedback(String s) {

        mSwipeRefreshLayout.setRefreshing(false);
        pg.setVisibility(View.GONE);
        ArrayList<get> list=new ArrayList<>();
        list= Jsonparser.getobjectlist(s);
       adapter.refresh(list);

    }

    public static void showpg() {
        pg.setVisibility(View.VISIBLE);
    }
}
