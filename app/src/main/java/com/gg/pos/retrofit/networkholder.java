package com.gg.pos.retrofit;

import com.gg.pos.Addbarcode;
import com.gg.pos.Brand;
import com.gg.pos.Category;
import com.gg.pos.Createuser;
import com.gg.pos.Inventory;
import com.gg.pos.Login;
import com.gg.pos.MainActivity;
import com.gg.pos.Model;
import com.gg.pos.Suppiler;
import com.gg.pos.Userdetail;
import com.gg.pos.object.Constant;
import com.gg.pos.utility.Stringconverter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Go Goal on 4/18/2017.
 */

public class networkholder {

    private static Object brandlist;
    private static Object itemlist_serandbar;

    public static void Logincheck(String user, String pass) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.Logincheck(user, pass);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Login.Feedback(response.body().toString());
                } catch (Exception e) {
                    Login.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Login.Feedback_error();
            }
        });

    }

    public static void createuser(String user, String pass, String fullname, String email, String address, String phone, String type) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.createuser(user, pass, fullname, email, address, phone, type);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Userdetail.Feedback(response.body().toString());
                } catch (Exception e) {
                    Userdetail.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Userdetail.Feedback_error();
            }
        });

    }

    public static void logout(String id, String userid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.logout(id, userid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    MainActivity.Feedback_logout(response.body().toString());
                } catch (Exception e) {
                    MainActivity.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MainActivity.Feedback_error();
            }
        });
    }

    public static void getbrandlist() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getbrandlist("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Brand.Feedback(response.body().toString());
                } catch (Exception e) {
                    Brand.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Brand.Feedback_error();
            }
        });
    }

    public static void addbrand(String s) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.addbrand(s);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Brand.Feedback(response.body().toString());
                } catch (Exception e) {
                    Brand.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Brand.Feedback_error();
            }
        });
    }

    public static void getModellist() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getModellist("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Model.Feedback(response.body().toString());
                } catch (Exception e) {
                    Model.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Model.Feedback_error();
            }
        });
    }

    public static void addmodel(String s, String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.addmodel(s, id);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Model.Feedback(response.body().toString());
                } catch (Exception e) {
                    Model.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Model.Feedback_error();
            }
        });
    }


    public static void getbrandlist_model() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getbrandlist("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Model.Feedback_getbrandlist(response.body().toString());
                } catch (Exception e) {
                    Model.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Model.Feedback_error();
            }
        });
    }


    public static void getCategorylist() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getCategorylist("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Category.Feedback(response.body().toString());
                } catch (Exception e) {
                    Category.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Category.Feedback_error();
            }
        });
    }

    public static void addcartegory(String s) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.addcartegory(s);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Category.Feedback(response.body().toString());
                } catch (Exception e) {
                    Category.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Category.Feedback_error();
            }
        });
    }

    public static void getsuppilerlist() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getsuppilerlist("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Suppiler.Feedback(response.body().toString());
                } catch (Exception e) {
                    Suppiler.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Suppiler.Feedback_error();
            }
        });
    }

    public static void addsuppiler(String cname, String caddress, String sname, String sdes, String sph, String semail) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.addsuppiler(cname, caddress, sname, sdes, sph, semail);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Suppiler.Feedback(response.body().toString());
                } catch (Exception e) {
                    Suppiler.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Suppiler.Feedback_error();
            }
        });
    }


    public static void getitemlist() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getitemlist("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Inventory.Feedback(response.body().toString());
                } catch (Exception e) {
                    Inventory.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Inventory.Feedback_error();
            }
        });

    }


    public static void additem(String des, String brand, String model, String cartegory, String price, String qty,String baseqty,String serial,boolean check) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        if (serial.length()<1){
            serial="0";
        }

        String apk="false";
        if (check){
            apk="true";
        }

        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.additem(des, brand, model, cartegory, price, qty,baseqty,serial,apk);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Inventory.Feedback(response.body().toString());
                } catch (Exception e) {
                    Inventory.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Inventory.Feedback_error();
            }
        });
    }


    public static void getitemlist_fromid() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getitemlistfromid("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Inventory.Feedback_fromid(response.body().toString());
                } catch (Exception e) {
                    Inventory.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Inventory.Feedback_error();
            }
        });
    }

    public static void getsuppiler_purchasedetail(final String itemid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.purchasedetail("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Inventory.Feedback_purchasedetail(response.body().toString(), itemid);
                } catch (Exception e) {
                    Inventory.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Inventory.Feedback_error();
            }
        });
    }

    public static void addqty(String itemid, String spid, String price, String qty) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.addqty(itemid, spid, price, qty);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Inventory.Feedback(response.body().toString());
                } catch (Exception e) {
                    Inventory.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Inventory.Feedback_error();
            }
        });
    }


    public static void getuser() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getuser("a");
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Userdetail.Feedback_userlist(response.body().toString());
                } catch (Exception e) {
                    Userdetail.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Userdetail.Feedback_error();
            }
        });
    }



    public static void getitemlist_serandbar(String itemid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.getitemlist_serandbar(itemid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Addbarcode.Feedback(response.body().toString());
                } catch (Exception e) {
                    Userdetail.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Addbarcode.Feedback_error();
            }
        });
    }

    public static void Addbarcodeandserial( String s, String s1,String itemid) {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.Addbarcodeandserial(s,s1,itemid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Addbarcode.Feedback(response.body().toString());
                } catch (Exception e) {
                    Userdetail.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Addbarcode.Feedback_error();
            }
        });
    }

    public static void caculatesell(String barcode, String qty) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.caculatesell(barcode,qty);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    MainActivity.Feedback_add(response.body().toString());
                } catch (Exception e) {
                    Userdetail.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MainActivity.Feedback_error();
            }
        });
    }

    public static void cash(String sellitemis, String sellqty) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.cash(sellitemis,sellqty);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    MainActivity.Feedback_cash(response.body().toString());
                } catch (Exception e) {
                    MainActivity.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                MainActivity.Feedback_error();
            }
        });
    }

    public static void deletebrand(String id) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.deletebrand(id);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Brand.Feedback(response.body().toString());
                } catch (Exception e) {
                    Brand.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Brand.Feedback_error();
            }
        });

    }

    public static void deletemodel(String modelid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.deletemodel(modelid);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Model.Feedback(response.body().toString());
                } catch (Exception e) {
                    Model.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Model.Feedback_error();
            }
        });
    }

    public static void deletecategory(String id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.host).addConverterFactory(new Stringconverter()).build();


        inerface api = retrofit.create(inerface.class);
        Call<String> result = api.deletecategory(id);
        result.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                try {
                    Category.Feedback(response.body().toString());
                } catch (Exception e) {
                    Category.Feedback_error();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Category.Feedback_error();
            }
        });
    }
}
