package com.reader.components;

import android.app.Application;
import android.content.Context;

import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by DongZ on 2015/9/23 0023.
 */
public class BaseApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
        PgyCrashManager.register(this);
    }

}
