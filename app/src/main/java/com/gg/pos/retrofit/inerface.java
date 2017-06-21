package com.gg.pos.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Go Goal on 4/18/2017.
 */

public interface inerface {

    @FormUrlEncoded
    @POST("ood/api/user/Logincheck")
    Call<String> Logincheck(@Field("name") String name, @Field("pass")String pass);

    @FormUrlEncoded
    @POST("ood/api/user/createuser")
    Call<String> createuser(@Field("name")String user,@Field("pass") String pass,@Field("fullname") String fullname,@Field("email") String email,@Field("address") String address, @Field("phone")String phone,@Field("type") String type);

    @FormUrlEncoded
    @POST("ood/api/user/logout")
    Call<String> logout(@Field("id")String id, @Field("userid")String userid);

    @FormUrlEncoded
    @POST("ood/api/inventory/getbrandlist")
    Call<String> getbrandlist(@Field("id")String a);


    @FormUrlEncoded
    @POST("ood/api/inventory/addbrand")
    Call<String> addbrand(@Field("name")String s);


    @FormUrlEncoded
    @POST("ood/api/inventory/getModellist")
    Call<String> getModellist(@Field("id")String a);

    @FormUrlEncoded
    @POST("ood/api/inventory/addmodel")
    Call<String> addmodel(@Field("name")String s,@Field("id")String id);



    @FormUrlEncoded
    @POST("ood/api/inventory/getCategorylist")
    Call<String> getCategorylist(@Field("id")String a);


    @FormUrlEncoded
    @POST("ood/api/inventory/addcartegory")
    Call<String> addcartegory(@Field("name")String s);


    @FormUrlEncoded
    @POST("ood/api/inventory/getsuppilerlist")
    Call<String> getsuppilerlist(@Field("id")String s);

    @FormUrlEncoded
    @POST("ood/api/inventory/addsuppiler")
    Call<String> addsuppiler(@Field("cname")String s,@Field("cadress")String ss,@Field("sname")String sa,@Field("sdes")String sf,@Field("sph")String asdfs,@Field("semail")String sasdf);


    @FormUrlEncoded
    @POST("ood/api/inventory/getitemlist")
    Call<String> getitemlist(@Field("id")String s);


    @FormUrlEncoded
    @POST("ood/api/inventory/additem")
    Call<String> additem(@Field("des")String s,@Field("brand")String ss,@Field("model")String sa,@Field("cartegory")String sf,@Field("price")String asdfs,@Field("qty")String sasdf,@Field("baseqty")String baseqty,@Field("barcode")String serial,@Field("check")String check);


    @FormUrlEncoded
    @POST("ood/api/inventory/getitemlistfromid")
    Call<String> getitemlistfromid(@Field("id")String s);

    @FormUrlEncoded
    @POST("ood/api/inventory/purchasedetail")
    Call<String> purchasedetail(@Field("id")String s);

    @FormUrlEncoded
    @POST("ood/api/inventory/addqty")
    Call<String> addqty(@Field("itemid")String s,@Field("spid")String ss,@Field("price")String sa,@Field("qty")String sf);


    @FormUrlEncoded
    @POST("ood/api/user/getuser")
    Call<String> getuser(@Field("id")String s);

    @FormUrlEncoded
    @POST("ood/api/inventory/getitemlist_serandbar")
    Call<String> getitemlist_serandbar(@Field("id")String s);


    @FormUrlEncoded
    @POST("ood/api/inventory/Addbarcodeandserial")
    Call<String> Addbarcodeandserial(@Field("serial")String serial,@Field("bar")String bar,@Field("id")String id);


    @FormUrlEncoded
    @POST("ood/api/inventory/caculatesell")
    Call<String> caculatesell(@Field("barcode")String barcode,@Field("qty")String qty);


    @FormUrlEncoded
    @POST("ood/api/inventory/cash")
    Call<String> cash(@Field("itemid")String itemid,@Field("qty")String qty);


    @FormUrlEncoded
    @POST("ood/api/inventory/deletebrand")
    Call<String> deletebrand(@Field("id")String s);


    @FormUrlEncoded
    @POST("ood/api/inventory/deletemodel")
    Call<String> deletemodel(@Field("id")String s);

    @FormUrlEncoded
    @POST("ood/api/inventory/deletecategory")
    Call<String> deletecategory(@Field("id")String s);



}
