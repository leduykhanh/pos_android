package com.gg.pos.utility;

import android.util.Log;

import com.gg.pos.object.get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Go Goal on 4/18/2017.
 */

public class Jsonparser {


    public static String getonestring(String str, String status) {
        JSONObject jo = null;
        String result = "";
        try {
            jo = new JSONObject(str);
            result = jo.getString(status);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return result;
    }

    public static ArrayList<String> getstringlist(String s) {

        ArrayList<String> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);

                strlist.add(c1.getString("name"));
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<String> getstringlist_model(String s) {
        ArrayList<String> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);

                strlist.add(c1.getString("name")+"   (Brand - "+c1.getString("brand_name")+" )");
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<String> getstringlist_withname(String s, String brandid) {

        ArrayList<String> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);

                strlist.add(c1.getString(brandid));
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<get> getobjectlist(String s) {
        ArrayList<get> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg=new get();
                eg.setCname(c1.getString("cname"));
                eg.setCaddress(c1.getString("caddress"));
                eg.setSname(c1.getString("sname"));
                eg.setSdes(c1.getString("sdes"));
                eg.setSph(c1.getString("sph"));
                eg.setSemail(c1.getString("semail"));

                strlist.add(eg);
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<get> getobjectlist_item(String s) {
        ArrayList<get> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg=new get();
                eg.setCheck(c1.getString("check"));
                eg.setItemid(c1.getString("itemid"));
                eg.setDescription(c1.getString("Description"));
                eg.setBrandname(c1.getString("brandname"));
                eg.setModelname(c1.getString("modelname"));
                eg.setCategory_name(c1.getString("category_name"));
                eg.setSelling_price(c1.getString("selling_price"));
                eg.setTotal_quantity(c1.getString("total_quantity"));
                eg.setBarcode(c1.getString("barcode"));

                strlist.add(eg);
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<get> getobjectlist_withname(String s, String brand) {
        ArrayList<get> strlist = new ArrayList<>();

        try {

            JSONObject job=new JSONObject(s);

            JSONArray id = job.getJSONArray(brand);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg=new get();
                eg.setId(c1.getString("id"));
                eg.setName(c1.getString("name"));
                strlist.add(eg);
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<get> getlist_user(String s) {
        ArrayList<get> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg=new get();
                eg.setUserid(c1.getString("userid"));
                eg.setUsername(c1.getString("username"));
                eg.setUserstatus(c1.getString("status"));
                eg.setUserfullname(c1.getString("name"));
                strlist.add(eg);
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<get> getobjectlist_barserialitem(String s) {
        ArrayList<get> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);
                get eg=new get();
                eg.setSerial(c1.getString("serial"));
                eg.setBarcode(c1.getString("barid"));
                strlist.add(eg);
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<get> getobjectstringlist(String s) {
        ArrayList<get> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);

                get eg=new get();
                eg.setBrandname(c1.getString("name"));
                eg.setId(c1.getString("brandid"));

                strlist.add(eg);
            }


        } catch (Exception e) {

        }

        return strlist;
    }

    public static ArrayList<get> getobjectstringlist_model(String s) {
        ArrayList<get> strlist = new ArrayList<>();

        try {

            JSONArray id = new JSONArray(s);
            for (int i = 0; i < id.length(); i++) {
                JSONObject c1 = id.getJSONObject(i);

                get eg=new get();
                eg.setModelname(c1.getString("name"));
                eg.setModelid(c1.getString("mid"));
                eg.setBrandname(c1.getString("brand_name"));
                eg.setBrandid(c1.getString("brandid"));

                strlist.add(eg);
            }


        } catch (Exception e) {

        }

        return strlist;
    }
}
