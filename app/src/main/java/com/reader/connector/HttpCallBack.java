package com.reader.connector;

/**
 * Created by Administrator on 2015/9/9.
 */
public interface HttpCallBack {
    public void onGeneralSuccess( String result , long flag ) ;
    public void onGeneralError( String e , long flag );
}
