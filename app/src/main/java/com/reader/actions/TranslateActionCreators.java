package com.reader.actions;

import com.apkfuns.logutils.LogUtils;
import com.reader.connector.HttpCallBack;
import com.reader.connector.protocol.TranslateProtocol;
import com.reader.dispatcher.Dispatcher;

/**
 * Created by DongZ on 2015/9/23 0023.
 */
public class TranslateActionCreators extends ActionsCreator{

    private static TranslateActionCreators instance;

    TranslateActionCreators() {
        super();
    }

    public static TranslateActionCreators getInstance() {
        if (instance == null) {
            synchronized (Dispatcher.class) {
                if (instance == null)
                    instance = new TranslateActionCreators();
            }
        }
        return instance;
    }

    public void TransLate(String eng){

        TranslateProtocol.youdaoTransLate(eng, new HttpCallBack() {
            @Override
            public void onGeneralSuccess(String result, long flag) {
                LogUtils.e(result);
            }

            @Override
            public void onGeneralError(String e, long flag) {

            }
        });
    }


    @Override
    public void destory(){
        instance = null;
        System.gc();
    }
}
