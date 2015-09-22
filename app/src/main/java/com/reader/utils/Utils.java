package com.reader.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2015/9/9.
 */
public class Utils {
    public static void toastMsg( Context context , String sMsg )
    {
        Toast.makeText(context, sMsg, Toast.LENGTH_SHORT).show( );
    }
}
