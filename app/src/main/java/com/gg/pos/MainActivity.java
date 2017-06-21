package com.gg.pos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.print.pdf.PrintedPdfDocument;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gg.pos.adapter.Mainadapter;
import com.gg.pos.functions.CashDrawerFunctions;
import com.gg.pos.functions.PrinterFunctions;
import com.gg.pos.localizereceipts.ILocalizeReceipts;
import com.gg.pos.object.get;
import com.gg.pos.retrofit.networkholder;
import com.gg.pos.utility.Jsonparser;
import com.gg.pos.utility.Mytoast;
import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.IConnectionCallback;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExtManager;
import com.starmicronics.starioextension.StarIoExtManagerListener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

//ဖင္ခံ
// Go Goal ss
//

public class MainActivity extends AppCompatActivity
        implements OnClickListener,IConnectionCallback, CommonAlertDialogFragment.Callback {
    private static final int MILS_IN_INCH = 1000;
    String userrow;
    static AppCompatActivity ac;
    TextView usernametv, userrowtv, titleusername;
    private Button checkOutButton;


    RecyclerView rv;
    EditText bared, qtyed;
    Button logoutlay;
    RelativeLayout addbtn, clearbtn;

    RelativeLayout[] btn = new RelativeLayout[10];
    RelativeLayout[] btnpayment = new RelativeLayout[4];
    RelativeLayout[] tv = new RelativeLayout[4];
    LinearLayout rvlayout;
    static ProgressBar pg;
    static Mainadapter adapter;
    float rvtvwidth;
    static ArrayList<get> list;
    private double gst, ddd;


    LinearLayout userlay, inventorylay, navoper;
    static TextView nettv, taxtv;
    static View myview;

    private ProgressDialog mProgressDialog;
    private int PRINTER_SET_REQUEST_CODE=1;
    private PrinterSetting setting;
    TextView textView,textView1;
    private StarIoExtManager mStarIoExtManager;
    private int mSelectedIndex;
    private int mLanguage;
    private int mPaperSize;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PRINTER_SET_REQUEST_CODE) {
            Toast.makeText(getApplicationContext(),"hello",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setting = new PrinterSetting(this);
        StarIoExt.Emulation emulation = setting.getEmulation();
        mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, setting.getPortName(), setting.getPortSettings(), 10000, getApplicationContext());     // 10000mS!!!
        mStarIoExtManager.setCashDrawerOpenActiveHigh(setting.getCashDrawerOpenActiveHigh());
        mLanguage=PrinterSetting.LANGUAGE_ENGLISH;
        mPaperSize=PrinterSetting.PAPER_SIZE_TWO_INCH;

        checkOutButton= (Button) findViewById(R.id.checkOut);
        checkOutButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() > 0) {
                    getTotalPrice();
                    AlertDialog.Builder ab = new AlertDialog.Builder(ac);
                    ab.setTitle("Sold");
                    ab.setMessage("Are You Sure To Sold Out?");
                    ab.setPositiveButton("Sold", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int gg) {
                            pg.setVisibility(View.VISIBLE);
                            for (int i = 0; i < list.size(); i++) {

                                networkholder.cash(list.get(i).getSellitemis(), list.get(i).getSellqty());
                            }

                            soldoutt = true;
                            bared.setText("");
                            qtyed.setText("");
//                            print();
                            hidefooter(0);

                            printing();
                            btnpayment[0].setBackgroundResource(R.drawable.money_copy);
                            btnpayment[1].setBackgroundResource(R.drawable.visa_logo_png_1024_x_642_copy);
                            btnpayment[2].setBackgroundResource(R.drawable.mastercard_png_23_copy);
                            btnpayment[3].setBackgroundResource(R.drawable.a20151208_56663_cbb_69879_copy);

                        }
                    }).setNegativeButton("Cancel", null).show();
                }
            }
        });
        ac = this;
        userrow = getIntent().getExtras().getString("status");
        list = new ArrayList<>();
        rv = (RecyclerView) findViewById(R.id.rv);
        bared = (EditText) findViewById(R.id.barcodeed);
        qtyed = (EditText) findViewById(R.id.qtyedd);
        rvlayout = (LinearLayout) findViewById(R.id.rvlayout);

        userlay = (LinearLayout) findViewById(R.id.user);
        inventorylay = (LinearLayout) findViewById(R.id.invent);
        logoutlay = (Button) findViewById(R.id.logoutbb);

        navoper = (LinearLayout) findViewById(R.id.navopener);


        nettv = (TextView) findViewById(R.id.netamounttv);
        taxtv = (TextView) findViewById(R.id.tax);
        myview = (View) findViewById(R.id.myview);


        hidefooter(0);


        btn[0] = (RelativeLayout) findViewById(R.id.btn0);
        btn[1] = (RelativeLayout) findViewById(R.id.btn1);
        btn[2] = (RelativeLayout) findViewById(R.id.btn2);
        btn[3] = (RelativeLayout) findViewById(R.id.btn3);
        btn[4] = (RelativeLayout) findViewById(R.id.btn4);
        btn[5] = (RelativeLayout) findViewById(R.id.btn5);
        btn[6] = (RelativeLayout) findViewById(R.id.btn6);
        btn[7] = (RelativeLayout) findViewById(R.id.btn7);
        btn[8] = (RelativeLayout) findViewById(R.id.btn8);
        btn[9] = (RelativeLayout) findViewById(R.id.btn9);
        addbtn = (RelativeLayout) findViewById(R.id.addbtn);
        clearbtn = (RelativeLayout) findViewById(R.id.clear);

        btnpayment[0] = (RelativeLayout) findViewById(R.id.btncash);
        btnpayment[1] = (RelativeLayout) findViewById(R.id.btnmaster);
        btnpayment[2] = (RelativeLayout) findViewById(R.id.btnvisa);
        btnpayment[3] = (RelativeLayout) findViewById(R.id.btnnet);
        //


        tv[0] = (RelativeLayout) findViewById(R.id.t1);
        tv[1] = (RelativeLayout) findViewById(R.id.t2);
        tv[2] = (RelativeLayout) findViewById(R.id.t3);
        tv[3] = (RelativeLayout) findViewById(R.id.t4);

        pg = (ProgressBar) findViewById(R.id.progressBar);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        navoper.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer.openDrawer(Gravity.LEFT);

            }
        });

       /* ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();*/


        usernametv = (TextView) findViewById(R.id.username);
        userrowtv = (TextView) findViewById(R.id.userrow);
        titleusername = (TextView) findViewById(R.id.titleusername);

        String name = getIntent().getExtras().getString("name");
        String row = getIntent().getExtras().getString("row");


        usernametv.setText(Html.fromHtml("<u>" + name + "</u>"));
        titleusername.setText(name);
        userrowtv.setText(row);


        for (int i = 0; i < btn.length; i++) {

            btn[i].setOnClickListener(this);

        }


        clearbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                bared.setText("");
                qtyed.setText("");
            }
        });


        addbtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                if (bared.getText().length() < 1) {
                    Mytoast.show(ac, "Please Enter Barcode");
                } else if (qtyed.getText().length() < 1) {
                    Mytoast.show(ac, "Please Enter QTY");
                } else {

                    btnpayment[0].setBackgroundResource(R.drawable.money);
                    btnpayment[1].setBackgroundResource(R.drawable.visa_logo_png_1024_x_642);
                    btnpayment[2].setBackgroundResource(R.drawable.mastercard_png_23);
                    btnpayment[3].setBackgroundResource(R.drawable.a20151208_56663_cbb_69879);

                    pg.setVisibility(View.VISIBLE);
                    String barstr = bared.getText().toString();
                    int qtystr = Integer.parseInt(qtyed.getText().toString());

                    for (int i = 0; i < list.size(); i++) {

                        if (list.get(i).getBarcode().equals(barstr)) {

                            int ttqty = Integer.parseInt(list.get(i).getSellqty());
                            qtystr = ttqty + qtystr;
                        }

                    }

                    networkholder.caculatesell(barstr, qtystr + "");
                }


            }
        });

        adapter = new Mainadapter(list, 0, getApplicationContext());
        rv.setAdapter(adapter);

        btnpayment[0].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() > 0) {
                    getTotalPrice();
                    AlertDialog.Builder ab = new AlertDialog.Builder(ac);
                    ab.setTitle("Sold");
                    ab.setMessage("Are You Sure To Sold Out?");
                    ab.setPositiveButton("Sold", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int gg) {
                            pg.setVisibility(View.VISIBLE);
                            for (int i = 0; i < list.size(); i++) {

                                networkholder.cash(list.get(i).getSellitemis(), list.get(i).getSellqty());
                            }

                            soldoutt = true;
                            bared.setText("");
                            qtyed.setText("");
//                            print();
                            hidefooter(0);

                            printing();
                            btnpayment[0].setBackgroundResource(R.drawable.money_copy);
                            btnpayment[1].setBackgroundResource(R.drawable.visa_logo_png_1024_x_642_copy);
                            btnpayment[2].setBackgroundResource(R.drawable.mastercard_png_23_copy);
                            btnpayment[3].setBackgroundResource(R.drawable.a20151208_56663_cbb_69879_copy);

                        }
                    }).setNegativeButton("Cancel", null).show();
                }

            }
        });
        btnpayment[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() > 0) {
                    getTotalPrice();

                    AlertDialog.Builder ab = new AlertDialog.Builder(ac);
                    ab.setTitle("Sold");
                    ab.setMessage("Are You Sure To Sold Out?");
                    ab.setPositiveButton("Sold", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int gg) {
                            pg.setVisibility(View.VISIBLE);
                            for (int i = 0; i < list.size(); i++) {

                                networkholder.cash(list.get(i).getSellitemis(), list.get(i).getSellqty());
                            }

                            soldoutt = true;
                            bared.setText("");
                            qtyed.setText("");
//                            print();
                            hidefooter(0);

                            printing();
                            btnpayment[0].setBackgroundResource(R.drawable.money_copy);
                            btnpayment[1].setBackgroundResource(R.drawable.visa_logo_png_1024_x_642_copy);
                            btnpayment[2].setBackgroundResource(R.drawable.mastercard_png_23_copy);
                            btnpayment[3].setBackgroundResource(R.drawable.a20151208_56663_cbb_69879_copy);

                        }
                    }).setNegativeButton("Cancel", null).show();
                }

            }
        });
        btnpayment[2].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() > 0) {
                    getTotalPrice();
                    AlertDialog.Builder ab = new AlertDialog.Builder(ac);
                    ab.setTitle("Sold");
                    ab.setMessage("Are You Sure To Sold Out?");
                    ab.setPositiveButton("Sold", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int gg) {
                            pg.setVisibility(View.VISIBLE);
                            for (int i = 0; i < list.size(); i++) {

                                networkholder.cash(list.get(i).getSellitemis(), list.get(i).getSellqty());
                            }

                            soldoutt = true;
                            bared.setText("");
                            qtyed.setText("");
//                            print();
                            hidefooter(0);

                            printing();
                            btnpayment[0].setBackgroundResource(R.drawable.money_copy);
                            btnpayment[1].setBackgroundResource(R.drawable.visa_logo_png_1024_x_642_copy);
                            btnpayment[2].setBackgroundResource(R.drawable.mastercard_png_23_copy);
                            btnpayment[3].setBackgroundResource(R.drawable.a20151208_56663_cbb_69879_copy);

                        }
                    }).setNegativeButton("Cancel", null).show();
                }

            }
        });
        btnpayment[3].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (list.size() > 0) {
                    getTotalPrice();

                    AlertDialog.Builder ab = new AlertDialog.Builder(ac);
                    ab.setTitle("Sold");
                    ab.setMessage("Are You Sure To Sold Out?");
                    ab.setPositiveButton("Sold", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int gg) {
                            pg.setVisibility(View.VISIBLE);
                            for (int i = 0; i < list.size(); i++) {

                                networkholder.cash(list.get(i).getSellitemis(), list.get(i).getSellqty());
                            }

                            soldoutt = true;
                            bared.setText("");
                            qtyed.setText("");
//                            print();
                            hidefooter(0);

                            printing();
                            btnpayment[0].setBackgroundResource(R.drawable.money_copy);
                            btnpayment[1].setBackgroundResource(R.drawable.visa_logo_png_1024_x_642_copy);
                            btnpayment[2].setBackgroundResource(R.drawable.mastercard_png_23_copy);
                            btnpayment[3].setBackgroundResource(R.drawable.a20151208_56663_cbb_69879_copy);


                        }
                    }).setNegativeButton("Cancel", null).show();
                }

            }
        });


        userlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                if (userrow.equals("1")) {
                    Intent it = new Intent(getApplicationContext(), Userdetail.class);
                    startActivity(it);
                } else {
                    Mytoast.show(getApplicationContext(), "Require Admin Permission");
                }

            }
        });

        inventorylay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), Inventory.class);
                startActivity(it);

            }
        });


        logoutlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
                ab.setTitle("Alert!");
                ab.setMessage("Are your sure to logout?");
                ab.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        SharedPreferences prefs = getSharedPreferences("setting", MODE_PRIVATE);
                        String row = prefs.getString("row", null);
                        String id = prefs.getString("id", null);
                        String userid = prefs.getString("userid", null);

                        if (!row.equals("1")) {
                            networkholder.logout(id, userid);
                        } else {
                            Intent it = new Intent(getApplicationContext(), Login.class);
                            startActivity(it);
                            finish();
                        }


                    }
                }).setNegativeButton("Cancel", null).show();

            }
        });


    }

    private void printing() {
        String modelName = setting.getModelName();
        if (modelName.isEmpty()){
            Intent intent = new Intent(this, CommonActivity.class);
            intent.putExtra(CommonActivity.BUNDLE_KEY_ACTIVITY_LAYOUT, R.layout.activity_printer_search);
            intent.putExtra(CommonActivity.BUNDLE_KEY_TOOLBAR_TITLE, "Search Port");
            intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_HOME_BUTTON, true);
            intent.putExtra(CommonActivity.BUNDLE_KEY_SHOW_RELOAD_BUTTON, true);
            startActivityForResult(intent,PRINTER_SET_REQUEST_CODE);
        }else{
            Myapp.setArrayList(list);
            mSelectedIndex=1;
            PrintTask printTask = new PrintTask();
            printTask.execute();
        }

    }

    private static void hidefooter(int k) {
        if (k < 1) {
            Myapp.setTotal(nettv.getText().toString());
            Myapp.setGst(taxtv.getText().toString());
            nettv.setVisibility(View.GONE);
            taxtv.setVisibility(View.GONE);
            myview.setVisibility(View.GONE);
        } else {
            nettv.setVisibility(View.VISIBLE);
            taxtv.setVisibility(View.VISIBLE);
            myview.setVisibility(View.VISIBLE);
        }


        double ddd = 0;
        double total = 0;
        double gst = 0;
        for (int i = 0; i < list.size(); i++) {
            double iop;
            try {
                iop = Double.parseDouble(list.get(i).getSellamount());
            } catch (Exception e) {
                iop = 0;
            }

            total = total + iop;
            gst = total * 0.07;
            ddd = total + gst;


        }

        nettv.setText(Html.fromHtml("Net Amount:     <font color='green'>$" + new DecimalFormat("##.##").format(ddd) + "</font>"));
        taxtv.setText(Html.fromHtml("(Inclusive <font color='green'>7% GST $" + new DecimalFormat(".##").format(gst) + ")</font>"));


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    static boolean soldoutt;


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 4) {
            MainActivity.this.moveTaskToBack(true);
        }

        return true;
    }

    private void getTotalPrice() {
        double total = 0;

        for (int i = 0; i < list.size(); i++) {
            double iop = 0;
            try {
                iop = Double.parseDouble(list.get(i).getSellamount());
            } catch (Exception e) {
                iop = 0;
            }

            total = total + iop;
            gst = total * 0.07;
            ddd = total + gst;

        }
    }

    public static void Feedback_error() {
        Mytoast.show(ac, "Network Fail");
        pg.setVisibility(View.GONE);
    }

    public static void Feedback_logout(String s) {
        Mytoast.show(ac, "Logout sucessful");
        Intent it = new Intent(ac, Login.class);
        ac.startActivity(it);
        ac.finish();
    }

    public static void Feedback_add(String s) {

        pg.setVisibility(View.GONE);
        String status = Jsonparser.getonestring(s, "status");
        if (status.equals("0")) {
            Mytoast.show(ac, Jsonparser.getonestring(s, "msg"));
        } else {

            String itemid = Jsonparser.getonestring(s, "itemid");
            boolean bb = false;
            for (int i = 0; i < list.size(); i++) {

                if (itemid.equals(list.get(i).getSellitemis())) {


                    list.get(i).setSellqty(Jsonparser.getonestring(s, "qty"));
                    list.get(i).setSellamount(Jsonparser.getonestring(s, "msg"));
                    bb = true;
                }

            }

            if (!bb) {
                get eg = new get();
                eg.setSellitemis(Jsonparser.getonestring(s, "itemid"));
                eg.setSelling_price(Jsonparser.getonestring(s, "price"));
                eg.setSellqty(Jsonparser.getonestring(s, "qty"));
                eg.setSellamount(Jsonparser.getonestring(s, "msg"));
                eg.setBarcode(Jsonparser.getonestring(s, "barcode"));
                list.add(eg);

            }

            adapter.refresh(list);
            hidefooter(list.size());
        }


    }

    @Override
    public void onClick(View view) {

        if (!bared.isFocused()) {

            for (int i = 0; i < btn.length; i++) {
                if (view == btn[i]) {
                    append(qtyed, i + "");
                }
            }


        } else {

            for (int i = 0; i < btn.length; i++) {
                if (view == btn[i]) {
                    append(bared, i + "");
                }
            }
        }


    }

    public void append(EditText mmed, String sar) {

        mmed.append(sar);

    }


    public static void Feedback_cash(String s) {

        if (soldoutt) {
            Mytoast.show(ac, "Sold out");
            pg.setVisibility(View.GONE);
            list = new ArrayList<>();
            adapter.refresh(list);
            soldoutt = false;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    private void print() {
//        final MotoGpStatAdapter motoGpStatAdapter = new MotoGpStatAdapter(list,
//                getLayoutInflater());
//        PrintManager printManager = (PrintManager) getSystemService(
//                Context.PRINT_SERVICE);
//        printManager.print("MotoGP stats",
//                new PrintDocumentAdapter() {
//                    private int mRenderPageWidth;
//                    private int mRenderPageHeight;
//                    private PrintAttributes mPrintAttributes;
//                    private PrintDocumentInfo mDocumentInfo;
//                    private Context mPrintContext;
//
//                    @Override
//                    public void onLayout(final PrintAttributes oldAttributes,
//                                         final PrintAttributes newAttributes,
//                                         final CancellationSignal cancellationSignal,
//                                         final LayoutResultCallback callback,
//                                         final Bundle metadata) {
//                        // If we are already cancelled, don't do any work.
//                        if (cancellationSignal.isCanceled()) {
//                            callback.onLayoutCancelled();
//                            return;
//                        }
//                        // Now we determined if the print attributes changed in a way that
//                        // would change the layout and if so we will do a layout pass.
//                        boolean layoutNeeded = false;
//                        final int density = Math.max(newAttributes.getResolution().getHorizontalDpi(),
//                                newAttributes.getResolution().getVerticalDpi());
//                        // Note that we are using the PrintedPdfDocument class which creates
//                        // a PDF generating canvas whose size is in points (1/72") not screen
//                        // pixels. Hence, this canvas is pretty small compared to the screen.
//                        // The recommended way is to layout the content in the desired size,
//                        // in this case as large as the printer can do, and set a translation
//                        // to the PDF canvas to shrink in. Note that PDF is a vector format
//                        // and you will not lose data during the transformation.
//                        // The content width is equal to the page width minus the margins times
//                        // the horizontal printer density. This way we get the maximal number
//                        // of pixels the printer can put horizontally.
//                        final int marginLeft = (int) (density * (float) newAttributes.getMinMargins()
//                                .getLeftMils() / MILS_IN_INCH);
//                        final int marginRight = (int) (density * (float) newAttributes.getMinMargins()
//                                .getRightMils() / MILS_IN_INCH);
//                        final int contentWidth = (int) (density * (float) newAttributes.getMediaSize()
//                                .getWidthMils() / MILS_IN_INCH) - marginLeft - marginRight;
//                        if (mRenderPageWidth != contentWidth) {
//                            mRenderPageWidth = contentWidth;
//                            layoutNeeded = true;
//                        }
//                        // The content height is equal to the page height minus the margins times
//                        // the vertical printer resolution. This way we get the maximal number
//                        // of pixels the printer can put vertically.
//                        final int marginTop = (int) (density * (float) newAttributes.getMinMargins()
//                                .getTopMils() / MILS_IN_INCH);
//                        final int marginBottom = (int) (density * (float) newAttributes.getMinMargins()
//                                .getBottomMils() / MILS_IN_INCH);
//                        final int contentHeight = (int) (density * (float) newAttributes.getMediaSize()
//                                .getHeightMils() / MILS_IN_INCH) - marginTop - marginBottom;
//                        if (mRenderPageHeight != contentHeight) {
//                            mRenderPageHeight = contentHeight;
//                            layoutNeeded = true;
//                        }
//                        // Create a context for resources at printer density. We will
//                        // be inflating views to render them and would like them to use
//                        // resources for a density the printer supports.
//                        if (mPrintContext == null || mPrintContext.getResources()
//                                .getConfiguration().densityDpi != density) {
//                            Configuration configuration = new Configuration();
//                            configuration.densityDpi = density;
//                            mPrintContext = createConfigurationContext(
//                                    configuration);
//                            mPrintContext.setTheme(android.R.style.Theme_Holo_Light);
//                        }
//                        // If no layout is needed that we did a layout at least once and
//                        // the document info is not null, also the second argument is false
//                        // to notify the system that the content did not change. This is
//                        // important as if the system has some pages and the content didn't
//                        // change the system will ask, the application to write them again.
//                        if (!layoutNeeded) {
//                            callback.onLayoutFinished(mDocumentInfo, false);
//                            return;
//                        }
//                        // For demonstration purposes we will do the layout off the main
//                        // thread but for small content sizes like this one it is OK to do
//                        // that on the main thread.
//                        // Store the data as we will layout off the main thread.
//                        final List<get> items = motoGpStatAdapter.cloneItems();
//                        get start1 = new get();
//                        start1.setSellitemis("");
//                        start1.setSellqty("         ADVANCE      LAP     PTE.        LTD.");
//                        start1.setSellamount("");
//                        get start2 = new get();
//                        start2.setSellqty("         5 Stadium Walk #01-54");
//                        get start3 = new get();
//                        start3.setSellqty("         Leisure Park Kallang");
//                        get start4 = new get();
//                        start4.setSellqty("         Singapore-397693");
//                        get start5 = new get();
//                        start5.setSellqty("         Phone: 8522 6255");
//                        get start6 = new get();
//                        start6.setSellqty("         GST / Co. Reg No:201216258G");
//                        get start7 = new get();
//                        start7.setSellqty("         Email:sales@advancelap.com");
//
//                        get start = new get();
//                        start.setSellitemis("ItemID");
//                        start.setSellqty("Qty");
//                        start.setSelling_price("Price");
//                        start.setSellamount("Amount");
//                        items.add(0, start);
//                        items.add(0, start7);
//                        items.add(0, start6);
//                        items.add(0, start5);
//                        items.add(0, start4);
//                        items.add(0, start3);
//                        items.add(1, start2);
//                        items.add(0, start1);
//                        get end = new get();
//                        end.setSellitemis("Net Amount: $" + new DecimalFormat("########.##").format(ddd));
//                        end.setSellqty("");
//                        items.add(end);
//                        get end1 = new get();
//                        end1.setSellitemis("Inclusive 7% GST: $" + new DecimalFormat("########.##").format(gst));
//                        end1.setSellqty("");
//                        items.add(end1);
//                        new AsyncTask<Void, Void, PrintDocumentInfo>() {
//                            @Override
//                            protected void onPreExecute() {
//                                // First register for cancellation requests.
//                                cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
//                                    @Override
//                                    public void onCancel() {
//                                        cancel(true);
//                                    }
//                                });
//                                // Stash the attributes as we will need them for rendering.
//
//                                mPrintAttributes = newAttributes;
//                            }
//
//                            @Override
//                            protected PrintDocumentInfo doInBackground(Void... params) {
//                                try {
//                                    // Create an adapter with the stats and an inflater
//                                    // to load resources for the printer density.
//                                    MotoGpStatAdapter adapter = new MotoGpStatAdapter(items,
//                                            (LayoutInflater) mPrintContext.getSystemService(
//                                                    Context.LAYOUT_INFLATER_SERVICE));
//                                    int currentPage = 0;
//                                    int pageContentHeight = 0;
//                                    int viewType = -1;
//                                    View view = null;
//                                    LinearLayout dummyParent = new LinearLayout(mPrintContext);
//                                    dummyParent.setOrientation(LinearLayout.VERTICAL);
//                                    final int itemCount = adapter.getCount();
//                                    for (int i = 0; i < itemCount; i++) {
//                                        // Be nice and respond to cancellation.
//                                        if (isCancelled()) {
//                                            return null;
//                                        }
//                                        // Get the next view.
//                                        final int nextViewType = adapter.getItemViewType(i);
//                                        if (viewType == nextViewType) {
//                                            view = adapter.getView(i, view, dummyParent);
//                                        } else {
//                                            view = adapter.getView(i, null, dummyParent);
//                                        }
//                                        viewType = nextViewType;
//                                        // Measure the next view
//                                        measureView(view);
//                                        // Add the height but if the view crosses the page
//                                        // boundary we will put it to the next page.
//                                        pageContentHeight += view.getMeasuredHeight();
//                                        if (pageContentHeight > mRenderPageHeight) {
//                                            pageContentHeight = view.getMeasuredHeight();
//                                            currentPage++;
//                                        }
//                                    }
//                                    // Create a document info describing the result.
//                                    PrintDocumentInfo info = new PrintDocumentInfo
//                                            .Builder("MotoGP_stats.pdf")
//                                            .setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT)
//                                            .setPageCount(currentPage + 1)
//                                            .build();
//                                    // We completed the layout as a result of print attributes
//                                    // change. Hence, if we are here the content changed for
//                                    // sure which is why we pass true as the second argument.
//                                    callback.onLayoutFinished(info, true);
//                                    return info;
//                                } catch (Exception e) {
//                                    // An unexpected error, report that we failed and
//                                    // one may pass in a human readable localized text
//                                    // for what the error is if known.
//                                    callback.onLayoutFailed(null);
//                                    throw new RuntimeException(e);
//                                }
//                            }
//
//                            @Override
//                            protected void onPostExecute(PrintDocumentInfo result) {
//                                // Update the cached info to send it over if the next
//                                // layout pass does not result in a content change.
//                                mDocumentInfo = result;
//                            }
//
//                            @Override
//                            protected void onCancelled(PrintDocumentInfo result) {
//                                // Task was cancelled, report that.
//                                callback.onLayoutCancelled();
//                            }
//                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
//                    }
//
//                    @Override
//                    public void onWrite(final PageRange[] pages,
//                                        final ParcelFileDescriptor destination,
//                                        final CancellationSignal cancellationSignal,
//                                        final WriteResultCallback callback) {
//                        // If we are already cancelled, don't do any work.
//                        if (cancellationSignal.isCanceled()) {
//                            callback.onWriteCancelled();
//                            return;
//                        }
//                        // Store the data as we will layout off the main thread.
//                        final List<get> items = motoGpStatAdapter.cloneItems();
//                        get start1 = new get();
//                        start1.setSellitemis("");
//                        start1.setSellqty("         ADVANCE      LAP     PTE.        LTD.");
//                        start1.setSellamount("");
//                        get start2 = new get();
//                        start2.setSellqty("         5 Stadium Walk #01-54");
//                        get start3 = new get();
//                        start3.setSellqty("         Leisure Park Kallang");
//                        get start4 = new get();
//                        start4.setSellqty("         Singapore-397693");
//                        get start5 = new get();
//                        start5.setSellqty("         Phone: 8522 6255");
//                        get start6 = new get();
//                        start6.setSellqty("         GST / Co. Reg No:201216258G");
//                        get start7 = new get();
//                        start7.setSellqty("         Email:sales@advancelap.com");
//
//                        get start = new get();
//                        start.setSellitemis("ItemID");
//                        start.setSellqty("Qty");
//                        start.setSelling_price("Price");
//                        start.setSellamount("Amount");
//                        items.add(0, start);
//
//                        items.add(0, start7);
//                        items.add(0, start6);
//                        items.add(0, start5);
//                        items.add(0, start4);
//                        items.add(0, start3);
//                        items.add(0, start2);
//                        items.add(0, start1);
//
//                        get end = new get();
//                        end.setSellitemis("Net Amount: $" + new DecimalFormat("########.##").format(ddd));
//                        end.setSellqty("");
//                        items.add(end);
//                        get end1 = new get();
//                        end1.setSellitemis("Inclusive 7% GST: $" + new DecimalFormat("########.##").format(gst));
//                        end1.setSellqty("");
//                        items.add(end1);
//                        new AsyncTask<Void, Void, Void>() {
//                            private final SparseIntArray mWrittenPages = new SparseIntArray();
//                            private final PrintedPdfDocument mPdfDocument = new PrintedPdfDocument(
//                                    MainActivity.this, mPrintAttributes);
//
//                            @Override
//                            protected void onPreExecute() {
//                                // First register for cancellation requests.
//                                cancellationSignal.setOnCancelListener(new CancellationSignal.OnCancelListener() {
//                                    @Override
//                                    public void onCancel() {
//                                        cancel(true);
//                                    }
//                                });
//                            }
//
//                            @Override
//                            protected Void doInBackground(Void... params) {
//                                // Go over all the pages and write only the requested ones.
//                                // Create an adapter with the stats and an inflater
//                                // to load resources for the printer density.
//                                MotoGpStatAdapter adapter = new MotoGpStatAdapter(items,
//                                        (LayoutInflater) mPrintContext.getSystemService(
//                                                Context.LAYOUT_INFLATER_SERVICE));
//                                int currentPage = -1;
//                                int pageContentHeight = 0;
//                                int viewType = -1;
//                                View view = null;
//                                PdfDocument.Page page = null;
//                                LinearLayout dummyParent = new LinearLayout(mPrintContext);
//                                dummyParent.setOrientation(LinearLayout.VERTICAL);
//                                // The content is laid out and rendered in screen pixels with
//                                // the width and height of the paper size times the print
//                                // density but the PDF canvas size is in points which are 1/72",
//                                // so we will scale down the content.
//                                final float scale = Math.min(
//                                        (float) mPdfDocument.getPageContentRect().width()
//                                                / mRenderPageWidth,
//                                        (float) mPdfDocument.getPageContentRect().height()
//                                                / mRenderPageHeight);
//                                final int itemCount = adapter.getCount();
//                                for (int i = 0; i < itemCount; i++) {
//                                    // Be nice and respond to cancellation.
//                                    if (isCancelled()) {
//                                        return null;
//                                    }
//                                    // Get the next view.
//                                    final int nextViewType = adapter.getItemViewType(i);
//                                    if (viewType == nextViewType) {
//                                        view = adapter.getView(i, view, dummyParent);
//                                    } else {
//                                        view = adapter.getView(i, null, dummyParent);
//                                    }
//                                    viewType = nextViewType;
//                                    // Measure the next view
//                                    measureView(view);
//                                    // Add the height but if the view crosses the page
//                                    // boundary we will put it to the next one.
//                                    pageContentHeight += view.getMeasuredHeight();
//                                    if (currentPage < 0 || pageContentHeight > mRenderPageHeight) {
//                                        pageContentHeight = view.getMeasuredHeight();
//                                        currentPage++;
//                                        // Done with the current page - finish it.
//                                        if (page != null) {
//                                            mPdfDocument.finishPage(page);
//                                        }
//                                        // If the page is requested, render it.
//                                        if (containsPage(pages, currentPage)) {
//                                            page = mPdfDocument.startPage(currentPage);
//                                            page.getCanvas().scale(scale, scale);
//                                            // Keep track which pages are written.
//                                            mWrittenPages.append(mWrittenPages.size(), currentPage);
//                                        } else {
//                                            page = null;
//                                        }
//                                    }
//                                    // If the current view is on a requested page, render it.
//                                    if (page != null) {
//                                        // Layout an render the content.
//                                        view.layout(0, 0, view.getMeasuredWidth(),
//                                                view.getMeasuredHeight());
//                                        view.draw(page.getCanvas());
//                                        // Move the canvas for the next view.
//                                        page.getCanvas().translate(0, view.getHeight());
//                                    }
//                                }
//                                // Done with the last page.
//                                if (page != null) {
//                                    mPdfDocument.finishPage(page);
//                                }
//                                // Write the data and return success or failure.
//                                try {
//                                    mPdfDocument.writeTo(new FileOutputStream(
//                                            destination.getFileDescriptor()));
//                                    // Compute which page ranges were written based on
//                                    // the bookkeeping we maintained.
//                                    PageRange[] pageRanges = computeWrittenPageRanges(mWrittenPages);
//                                    callback.onWriteFinished(pageRanges);
//                                } catch (IOException ioe) {
//                                    callback.onWriteFailed(null);
//                                } finally {
//                                    mPdfDocument.close();
//                                }
//                                return null;
//                            }
//
//                            @Override
//                            protected void onCancelled(Void result) {
//                                // Task was cancelled, report that.
//                                callback.onWriteCancelled();
//                                mPdfDocument.close();
//                            }
//                        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Void[]) null);
//                    }
//
//                    private void measureView(View view) {
//                        final int widthMeasureSpec = ViewGroup.getChildMeasureSpec(
//                                View.MeasureSpec.makeMeasureSpec(mRenderPageWidth,
//                                        View.MeasureSpec.EXACTLY), 0, view.getLayoutParams().width);
//                        final int heightMeasureSpec = ViewGroup.getChildMeasureSpec(
//                                View.MeasureSpec.makeMeasureSpec(mRenderPageHeight,
//                                        View.MeasureSpec.EXACTLY), 0, view.getLayoutParams().height);
//                        view.measure(widthMeasureSpec, heightMeasureSpec);
//                    }
//
//                    private PageRange[] computeWrittenPageRanges(SparseIntArray writtenPages) {
//                        List<PageRange> pageRanges = new ArrayList<PageRange>();
//                        int start = -1;
//                        int end = -1;
//                        final int writtenPageCount = writtenPages.size();
//                        for (int i = 0; i < writtenPageCount; i++) {
//                            if (start < 0) {
//                                start = writtenPages.valueAt(i);
//                            }
//                            int oldEnd = end = start;
//                            while (i < writtenPageCount && (end - oldEnd) <= 1) {
//                                oldEnd = end;
//                                end = writtenPages.valueAt(i);
//                                i++;
//                            }
//                            PageRange pageRange = new PageRange(start, end);
//                            pageRanges.add(pageRange);
//                            start = end = -1;
//                        }
//                        PageRange[] pageRangesArray = new PageRange[pageRanges.size()];
//                        pageRanges.toArray(pageRangesArray);
//                        return pageRangesArray;
//                    }
//
//                    private boolean containsPage(PageRange[] pageRanges, int page) {
//                        final int pageRangeCount = pageRanges.length;
//                        for (int i = 0; i < pageRangeCount; i++) {
//                            if (pageRanges[i].getStart() <= page
//                                    && pageRanges[i].getEnd() >= page) {
//                                return true;
//                            }
//                        }
//                        return false;
//                    }
//                }, new PrintAttributes.Builder().setMediaSize(PrintAttributes.MediaSize.ISO_B7).build());
//    }

    @Override
    public void onDialogResult(String tag, Intent data) {

    }

    @Override
    public void onConnected(ConnectResult connectResult) {
        if (connectResult == ConnectResult.Success || connectResult == ConnectResult.AlreadyConnected) {

            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
        else {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }

    @Override
    public void onDisconnected() {

    }

    private class MotoGpStatAdapter extends BaseAdapter {
        private final List<get> mItems;
        private final LayoutInflater mInflater;

        public MotoGpStatAdapter(List<get> items, LayoutInflater inflater) {
            mItems = items;
            mInflater = inflater;
        }

        public List<get> cloneItems() {
            return new ArrayList<get>(mItems);
        }

        @Override
        public int getCount() {
            return mItems.size();
        }

        @Override
        public Object getItem(int position) {
            return mItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.motogp_stat_item, parent, false);
            }
            get item = (get) getItem(position);
            TextView yearView = (TextView) convertView.findViewById(R.id.year);
            yearView.setText(item.getSellitemis());
            TextView championView = (TextView) convertView.findViewById(R.id.champion);
            championView.setText(item.getSellqty());
            TextView constructorView = (TextView) convertView.findViewById(R.id.constructor);
            constructorView.setText(item.getSelling_price());
            TextView bbb = (TextView) convertView.findViewById(R.id.bbb);
            bbb.setText(item.getSellamount());
            return convertView;
        }
    }
    private class DrawerTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            //  mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            byte[] data;

            PrinterSetting setting = new PrinterSetting(MainActivity.this);
            StarIoExt.Emulation emulation = setting.getEmulation();

            boolean doCheckCondition = mSelectedIndex == 1 || mSelectedIndex == 3;

            switch (mSelectedIndex) {
                case 1:
                case 2:
                default:
                    data = CashDrawerFunctions.createData(emulation, ICommandBuilder.PeripheralChannel.No1);
                    break;
                case 3:
                case 4:
                    data = CashDrawerFunctions.createData(emulation, ICommandBuilder.PeripheralChannel.No2);
                    break;
            }

            if (doCheckCondition) {
                Communication.sendCommands(mStarIoExtManager, data, mStarIoExtManager.getPort(), mCallback);
            }
            else {
                Communication.sendCommandsDoNotCheckCondition(mStarIoExtManager, data, mStarIoExtManager.getPort(), mCallback);
            }

            return null;
        }

        private Communication.SendCallback mCallback = new Communication.SendCallback() {
            @Override
            public void onStatus(boolean result, Communication.Result communicateResult) {



                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                String msg;

                switch (communicateResult) {
                    case Success:
                        msg = "Success!";
                        break;
                    case ErrorOpenPort:
                        msg = "Fail to openPort";
                        break;
                    case ErrorBeginCheckedBlock:
                        msg = "Printer is offline (beginCheckedBlock)";
                        break;
                    case ErrorEndCheckedBlock:
                        msg = "Printer is offline (endCheckedBlock)";
                        break;
                    case ErrorReadPort:
                        msg = "Read port error (readPort)";
                        break;
                    case ErrorWritePort:
                        msg = "Write port error (writePort)";
                        break;
                    default:
                        msg = "Unknown error";
                        break;
                }

                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();

            }
        };
    }
    private class PrintTask extends AsyncTask<Void, Void, Communication.Result> {
        @Override
        protected void onPreExecute() {
            //   mProgressDialog.show();
        }

        @Override
        protected Communication.Result doInBackground(Void... params) {
            byte[] data;

            PrinterSetting setting = new PrinterSetting(getApplicationContext());
            StarIoExt.Emulation emulation = setting.getEmulation();

            ILocalizeReceipts localizeReceipts = ILocalizeReceipts.createLocalizeReceipts(mLanguage, mPaperSize);

            switch (mSelectedIndex) {
                default:
                case 1:
                    data = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, false);
                    break;
                case 2:
                    data = PrinterFunctions.createTextReceiptData(emulation, localizeReceipts, true);
                    break;
                case 3:
                    data = PrinterFunctions.createRasterReceiptData(emulation, localizeReceipts, getResources());
                    break;
                case 4:
                    data = PrinterFunctions.createScaleRasterReceiptData(emulation, localizeReceipts, getResources(), mPaperSize, true);
                    break;
                case 5:
                    data = PrinterFunctions.createScaleRasterReceiptData(emulation, localizeReceipts, getResources(), mPaperSize, false);
                    break;
                case 6:
                    data = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), mPaperSize, ICommandBuilder.BitmapConverterRotation.Normal);
                    break;
                case 7:
                    data = PrinterFunctions.createCouponData(emulation, localizeReceipts, getResources(), mPaperSize, ICommandBuilder.BitmapConverterRotation.Right90);
                    break;
            }

            Communication.sendCommands(mStarIoExtManager, data, mStarIoExtManager.getPort(), mCallback);     // 10000mS!!!

            return null;
        }

        private Communication.SendCallback mCallback = new Communication.SendCallback() {
            @Override
            public void onStatus(boolean result, Communication.Result communicateResult) {


                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }

                String msg;

                switch (communicateResult) {
                    case Success:
                        msg = "Success!";
                        break;
                    case ErrorOpenPort:
                        msg = "Fail to openPort";
                        break;
                    case ErrorBeginCheckedBlock:
                        msg = "Printer is offline (beginCheckedBlock)";
                        break;
                    case ErrorEndCheckedBlock:
                        msg = "Printer is offline (endCheckedBlock)";
                        break;
                    case ErrorReadPort:
                        msg = "Read port error (readPort)";
                        break;
                    case ErrorWritePort:
                        msg = "Write port error (writePort)";
                        break;
                    default:
                        msg = "Unknown error";
                        break;
                }

                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                DrawerTask drawerTask = new DrawerTask();
                drawerTask.execute();
            }
        };
    }
    private final StarIoExtManagerListener mStarIoExtManagerListener = new StarIoExtManagerListener() {
        @Override
        public void onPrinterImpossible() {
          Toast.makeText(getApplicationContext(),"Printer Impossible.",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCashDrawerOpen() {

            Toast.makeText(getApplicationContext(),"Cash Drawer Open.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCashDrawerClose() {

            Toast.makeText(getApplicationContext(),"Cash Drawer Close.",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onAccessoryConnectSuccess() {

            Toast.makeText(getApplicationContext(),"Accessory Connect Success.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAccessoryConnectFailure() {

            Toast.makeText(getApplicationContext(),"Accessory Connect Failure.",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onAccessoryDisconnect() {

            Toast.makeText(getApplicationContext(),"Accessory Connect Disconnect.",Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onStart() {
        super.onStart();

        mStarIoExtManager.setListener(mStarIoExtManagerListener);



        mStarIoExtManager.connect(this);
    }
    @Override
    public void onStop() {
        super.onStop();

        mStarIoExtManager.disconnect(this);
    }

}
