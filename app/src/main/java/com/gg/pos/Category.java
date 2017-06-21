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
import android.widget.TextView;

import com.gg.pos.adapter.Brandadapter;
import com.gg.pos.adapter.Categoryadapter;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Jsonparser;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/19/2017.
 */
public class Category extends AppCompatActivity{

    RecyclerView rv;
    static Categoryadapter adapter;
    static  AppCompatActivity ac;
    static ProgressBar pg;
    static SwipeRefreshLayout mSwipeRefreshLayout;

    LinearLayout navopener;
    TextView createuserbtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview);



        ac=this;
        rv= (RecyclerView) findViewById(R.id.rv);
        pg= (ProgressBar) findViewById(R.id.progressBar);
        mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.refresh_layout);

        navopener= (LinearLayout) findViewById(R.id.navopener);
        createuserbtn= (TextView) findViewById(R.id.creatuserbtn);

        navopener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        createuserbtn.setText("Create Category");


        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);



        adapter=new Categoryadapter(ac,new ArrayList<get>());
        rv.setAdapter(adapter);


        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkholder.getCategorylist();
            }
        });

        networkholder.getCategorylist();


        createuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it=new Intent(getApplicationContext(),Addcategory.class);
                startActivity(it);
                overridePendingTransition(0, 0);

            }
        });

               /* AlertDialog.Builder ab=new AlertDialog.Builder(ac);
                ab.setTitle("Add Category");
                View v=getLayoutInflater().inflate(R.layout.addbrand,null);
                final EditText ed= (EditText) v.findViewById(R.id.branded);
                ed.setHint("Enter Category Name");
                ab.setView(v);
                ab.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (ed.getText().length()<1){
                            Mytoast.show(ac, "Please Enter Category Name");
                        }else{
                            pg.setVisibility(View.VISIBLE);
                            networkholder.addcartegory(ed.getText().toString());
                        }


                    }
                }).setNegativeButton("Cancel",null).show();*/



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
        list= Jsonparser.getobjectstringlist(s);
        adapter.refresh(list);

    }


    public static void showpg() {
        pg.setVisibility(View.VISIBLE);
    }
}
