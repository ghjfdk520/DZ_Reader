package com.reader.components;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.reader.actions.ActionsCreator;
import com.reader.dispatcher.Dispatcher;
import com.reader.stores.Store;

/**
 * Created by DongZ on 2015/9/23 0023.
 */
public abstract class SuperActivity extends Activity{

    protected Context mContext;
    private long currentFlag;

    protected Store store;
    protected Dispatcher dispatcher;
    protected ActionsCreator actionsCreator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        onCreate(savedInstanceState, true);
    }

    protected void onCreate(Bundle savedInstanceState, boolean addToStack) {
        super.onCreate(savedInstanceState);
        // if(!Config.DEBUG)
        //Thread.setDefaultUncaughtExceptionHandler(this);
        mContext = this;
        if (addToStack)
            CloseAllActivity.getInstance().addActivity(this);
        initDependencies();
    }

    public void initDependencies(){
        dispatcher = Dispatcher.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dispatcher.register(this);
        dispatcher.register(store);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dispatcher.unregister(this);
        dispatcher.unregister(store);
        actionsCreator.destory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CloseAllActivity.getInstance().removeActivity(this);
    }


    /**
     * 获取string
     *
     * @param id
     * @return
     */
    public String getResString(int id) {
        return getResources().getString(id);
    }

    /**
     * 获取图片资源
     *
     * @param id
     * @return
     */
    public Drawable getResDrawable(int id) {
        return getResources().getDrawable(id);
    }

    /**
     * 获取color值
     *
     * @param id
     * @return
     */
    public int getResColor(int id) {
        return getResources().getColor(id);
    }

}
