package com.reader.components;

import android.app.Application;

import com.pgyersdk.crash.PgyCrashManager;

/**
 * Created by DongZ on 2015/9/23 0023.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this, "1f88b99df1b284c9a1614a1cd4fbd8ea");
    }

}
