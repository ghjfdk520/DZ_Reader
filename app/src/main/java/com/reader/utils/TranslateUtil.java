package com.reader.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.reader.common.BaseApplication;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * Created by DongZ on 2015/9/23 0023.
 */
public class TranslateUtil {

    private static String vendor;
    private static Context mContext = BaseApplication.mContext;
    public static String model = escapeSource(Build.MODEL);
    public static String mid = encode(Build.VERSION.RELEASE);
    public static String getIMEI() {
        String str = ((TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
        if (str == null)
            str = UUID.randomUUID().toString();

        return str;
    }



    public static String getMisc()
    {

        DisplayMetrics localDisplayMetrics = mContext.getResources().getDisplayMetrics();
        int screenWidth = localDisplayMetrics.widthPixels;
        int screenHeight = localDisplayMetrics.heightPixels;
        String screen = localDisplayMetrics.widthPixels + "x" + localDisplayMetrics.heightPixels;
        return screen;
    }



    private static String escapeSource(String paramString)
    {
        StringBuilder localStringBuilder = new StringBuilder();
        char[] arrayOfChar = paramString.toCharArray();
        int i = arrayOfChar.length;
        int j = 0;
        if (j >= i)
            return localStringBuilder.toString();
        char c = arrayOfChar[j];
        if ((c >= 'a') && (c <= 'z'))
            localStringBuilder.append(c);
        while (true)
        {
            ++j;
            if (j >= i)
                return localStringBuilder.toString();
            if ((c >= 'A') && (c <= 'Z'))
                localStringBuilder.append(c);
            if ((c >= '0') && (c <= '9'))
                localStringBuilder.append(c);
            if ((c == '.') || (c == '_') || (c == '-') || (c == '/'))
                localStringBuilder.append(c);
            if (c != ' ')
                continue;
            localStringBuilder.append('_');
        }
    }


    private static String encode(String paramString)
    {
        String str1;
        try
        {
            String str2 = URLEncoder.encode(paramString, "utf-8");
            str1 = str2;
            return str1;
        }
        catch (Exception localException)
        {
            str1 = "";
        }
        return "";
    }


    public static String version()
    {
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }



    public static String getVendor()
    {
        try
        {
            InputStream localInputStream = mContext.getAssets().open("vendor.txt");
            vendor = new BufferedReader(new InputStreamReader(localInputStream)).readLine();
            localInputStream.close();
            if (!(TextUtils.isEmpty(vendor)))
            {
                vendor = vendor.trim();
                return vendor;
            }
            vendor = "EMPTY_VENDOR";
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
            vendor = "NO_SET_VENDOR";
        }
        catch (IOException localIOException)
        {
            vendor = "ERROR_VENDOR";
        }

        return vendor;
    }


    public static String keyFrom()
    {
        return "fanyi." + version() + ".android";
    }
}
