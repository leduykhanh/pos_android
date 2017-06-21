package com.gg.pos;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.view.ViewGroup.LayoutParams;

import com.gg.pos.adapter.Brandadapter;
import com.gg.pos.adapter.Itemadapter;
import com.gg.pos.adapter.Spinneradapter;
import com.gg.pos.adapter.Spinnerbrandadapter;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Jsonparser;
import com.gg.pos.utility.Mytoast;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Go Goal on 4/19/2017.
 */

public class Inventory extends AppCompatActivity {


    RecyclerView rv;
    static Itemadapter adapter;
    static AppCompatActivity ac;
    static ProgressBar pg;
    static SwipeRefreshLayout mSwipeRefreshLayout;
    static Spinner sp1, sp2, sp3, spp;


    LinearLayout navopener;
    TextView createuserbtn;

    ImageView menupop;


    TextView l1,l2,l3,l4;

    ListView listViewSort;
    public static ArrayList<get> getspinnerlist() {
        return splist;
    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory);


        navopener= (LinearLayout) findViewById(R.id.navopener);
        createuserbtn= (TextView) findViewById(R.id.creatuserbtn);

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


        adapter = new Itemadapter(new ArrayList<get>(),0);
        rv.setAdapter(adapter);


        mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.CYAN);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkholder.getitemlist();
            }
        });

        networkholder.getitemlist();

        createuserbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pg.setVisibility(View.VISIBLE);
                networkholder.getitemlist_fromid();

            }
        });



        l1= (TextView) findViewById(R.id.l1);
//        l2= (TextView) findViewById(R.id.l2);
        l3= (TextView) findViewById(R.id.l3);
        l4= (TextView) findViewById(R.id.l4);
        final RelativeLayout poprl= (RelativeLayout) findViewById(R.id.poprl);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), Brand.class);
                startActivity(it);
                poprl.setVisibility(View.GONE);
            }
        });

//        l2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it1 = new Intent(getApplicationContext(), Model.class);
//                startActivity(it1);
//                poprl.setVisibility(View.GONE);
//            }
//        });

        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it2 = new Intent(getApplicationContext(), Category.class);
                startActivity(it2);
                poprl.setVisibility(View.GONE);
            }
        });

        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it3 = new Intent(getApplicationContext(), Suppiler.class);
                startActivity(it3);
                poprl.setVisibility(View.GONE);
            }
        });



        poprl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                poprl.setVisibility(View.GONE);
            }
        });

        menupop= (ImageView) findViewById(R.id.menupop);
        menupop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                poprl.setVisibility(View.VISIBLE);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (id == R.id.brand) {

            Intent it = new Intent(getApplicationContext(), Brand.class);
            startActivity(it);

        }
//        if (id == R.id.model) {
//            Intent it = new Intent(getApplicationContext(), Model.class);
//            startActivity(it);
//        }
        if (id == R.id.cartegory) {
            Intent it = new Intent(getApplicationContext(), Category.class);
            startActivity(it);
        }
        if (id == R.id.suppiler) {
            Intent it = new Intent(getApplicationContext(), Suppiler.class);
            startActivity(it);
        }


        return super.onOptionsItemSelected(item);
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
        list = Jsonparser.getobjectlist_item(s);
        adapter.refresh(list);


    }

    static ArrayList<get> brand, model, category;

    public static void Feedback_fromid(String s) {

        pg.setVisibility(View.GONE);
        brand = new ArrayList<>();
        model = new ArrayList<>();
        category = new ArrayList<>();

        brand = Jsonparser.getobjectlist_withname(s, "brand");
        model = Jsonparser.getobjectlist_withname(s, "model");
        category = Jsonparser.getobjectlist_withname(s, "category");

        Intent it = new Intent(ac, Additem.class);
        ac.startActivity(it);
        ac.overridePendingTransition(0, 0);




    }


    public static ArrayList<get> getbrandlist() {
        return brand;
    }

    public static ArrayList<get> getmodlelist() {
        return model;
    }

    public static ArrayList<get> getcategorylist() {
        return category;
    }




    static ArrayList<get> splist;

    public static void Feedback_purchasedetail(String s, final String itemid) {

        splist = new ArrayList<>();
        splist = Jsonparser.getobjectlist_withname(s, "arr");

        pg.setVisibility(View.GONE);

        Intent it=new Intent(ac,Addsupplierinstock.class);
        it.putExtra("itemid", itemid);
        ac.startActivity(it);
        ac.overridePendingTransition(0, 0);




    }

    public static void _purchasedetail(String itemid) {
        pg.setVisibility(View.VISIBLE);
        networkholder.getsuppiler_purchasedetail(itemid);
    }

    public static void gonextbarcode(String itemid, String con) {

        if (con.equals("false")) {
            Intent it = new Intent(ac, Addbarcode.class);
            it.putExtra("itemid", itemid);
            ac.startActivity(it);

        }


    }


    public static void gettoprintclass(String barcode11) {


        Intent it = new Intent(ac, Printbarcode.class);
        it.putExtra("a", barcode11);
        ac.startActivity(it);
        ac.overridePendingTransition(0, 0);

    }

    public static void showpg() {
        pg.setVisibility(View.VISIBLE);
    }
}
