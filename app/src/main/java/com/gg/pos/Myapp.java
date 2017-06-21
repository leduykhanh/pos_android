package com.gg.pos;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.gg.pos.object.get;

import java.util.ArrayList;

/**
 * Created by goldyonwar on 6/6/17.
 */

public class Myapp extends Application {
    public static Context context;
    private static ArrayList<get> arrayList;
    private static Bitmap bitmap;
    private static String total;
    private static String gst;
    private static boolean aBoolean;

    public static String getTotal() {
        return total;
    }

    public static void setTotal(String total) {
        Myapp.total = total;
    }

    public static String getGst() {
        return gst;
    }

    public static void setGst(String gst) {
        Myapp.gst = gst;
    }

    public static boolean isaBoolean() {
        return aBoolean;
    }

    public static void setaBoolean(boolean aBoolean) {
        Myapp.aBoolean = aBoolean;
    }

    public static Bitmap getBitmap() {
        return bitmap;
    }

    public static void setBitmap(Bitmap bitmap) {
        Myapp.bitmap = bitmap;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }

    public Context getContext() {
        return context;
    }
    public static Context getAppContext() {
        return Myapp.context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

    public static ArrayList<get> getArrayList() {
        return arrayList;
    }

    public static void setArrayList(ArrayList<get> arrayList) {
        Myapp.arrayList = arrayList;
    }
}
