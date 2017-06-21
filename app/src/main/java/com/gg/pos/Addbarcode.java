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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gg.pos.adapter.Addbarcodeadapter;
import com.gg.pos.adapter.Itemadapter;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Jsonparser;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Go Goal on 4/23/2017.
 */
public class Addbarcode extends AppCompatActivity{

    String itemid;
    static Addbarcodeadapter adapter;


    RecyclerView rv;
    static AppCompatActivity ac;
    static ProgressBar pg;
    static SwipeRefreshLayout mSwipeRefreshLayout;

    LinearLayout navopener;
    TextView createuserbtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addbarcode);

        navopener= (LinearLayout) findViewById(R.id.navopener);
        createuserbtn= (TextView) findViewById(R.id.creatuserbtn);

        navopener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        itemid=getIntent().getExtras().getString("itemid");

        ac = this;
        rv = (RecyclerView) findViewById(R.id.rv);
        pg = (ProgressBar) findViewById(R.id.progressBar);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);


        networkholder.getitemlist_serandbar(itemid);


        adapter = new Addbarcodeadapter(new ArrayList<get>(), 0,getApplicationContext());
        rv.setAdapter(adapter);


        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkholder.getitemlist_serandbar(itemid);
            }
        });



        createuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent it=new Intent(getApplicationContext(),Addbarcodeforuncheck.class);
                it.putExtra("itemid", itemid);
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
        ArrayList<get> list = new ArrayList<>();
        list = Jsonparser.getobjectlist_barserialitem(s);
        adapter.refresh(list);

    }

    public static void printclass(String barcode) {
        Intent it = new Intent(ac, Printbarcode.class);
        it.putExtra("a", barcode);
        ac.startActivity(it);
    }

    public static void showpg() {
        pg.setVisibility(View.VISIBLE);
    }
}
