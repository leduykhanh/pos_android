package com.gg.pos.utility;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Go Goal on 4/18/2017.
 */

public class Mytoast {

    public static void show(Context con,String str){

        Toast.makeText(con,str,Toast.LENGTH_SHORT).show();

    }
}
