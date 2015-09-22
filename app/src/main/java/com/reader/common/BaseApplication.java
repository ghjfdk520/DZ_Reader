package com.reader.common;

import android.app.Application;
import android.content.Context;

import com.apkfuns.logutils.LogUtils;
import com.pgyersdk.crash.PgyCrashManager;
import com.reader.conf.Config;

/**
 * Created by Administrator on 2015/9/9.
 */
public class BaseApplication extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext = getBaseContext();
        PgyCrashManager.register(this, "66a0cbb2f41686b59a52833aa45d0442");
        LogUtils.configAllowLog = Config.DEBUG;
    }
}
