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
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.gg.pos.adapter.Brandadapter;
import com.gg.pos.adapter.Modeladapter;
import com.gg.pos.adapter.Spinneradapter;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Jsonparser;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/19/2017.
 */
public class Model extends AppCompatActivity{

    RecyclerView rv;
    static Modeladapter adapter;
    static AppCompatActivity ac;
    static ProgressBar pg;
    static SwipeRefreshLayout mSwipeRefreshLayout;


    LinearLayout navopener;
    TextView createuserbtn;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modelrecyclerview);



        ac = this;
        rv = (RecyclerView) findViewById(R.id.rv);
        pg = (ProgressBar) findViewById(R.id.progressBar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        navopener = (LinearLayout) findViewById(R.id.navopener);
        createuserbtn = (TextView) findViewById(R.id.creatuserbtn);
        navopener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);


        adapter = new Modeladapter(ac, new ArrayList<get>());
        rv.setAdapter(adapter);


        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkholder.getModellist();
            }
        });

        networkholder.getModellist();


        createuserbtn.setText("Create Model");

        createuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pg.setVisibility(View.VISIBLE);
                networkholder.getbrandlist_model();


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
        list= Jsonparser.getobjectstringlist_model(s);
        adapter.refresh(list);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem)
    {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    static   ArrayList<String> list_id,splist;

    public static void Feedback_getbrandlist(String s) {

        pg.setVisibility(View.GONE);
        splist=new ArrayList<>();
        list_id=new ArrayList<>();
        splist= Jsonparser.getstringlist(s);
        list_id=Jsonparser.getstringlist_withname(s,"brandid");

        Intent it=new Intent(ac,Addmodel.class);
        ac.startActivity(it);
        ac.overridePendingTransition(0, 0);








    }

    public static ArrayList<String> getspinnerlist () {
        return splist;
    }

    public static ArrayList<String> getspinnerlist_id() {
        return list_id;
    }

    public static void showpg() {
        pg.setVisibility(View.VISIBLE);
    }
}
