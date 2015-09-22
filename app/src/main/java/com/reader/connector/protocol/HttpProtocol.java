package com.reader.connector.protocol;

import com.reader.connector.ConnectorManage;
import com.reader.connector.HttpCallBack;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2015/9/9.
 */
public class HttpProtocol {
    public static long Post( String url,
                             LinkedHashMap<String, Object> map, HttpCallBack callback) {
        return ConnectorManage.getInstance().Post(url, map, callback);
    }

    public static long test(HttpCallBack callback){
        LinkedHashMap< String , Object > entity = new LinkedHashMap< String , Object >( );
        entity.put( "tn" , "myie2dg");
        return Post("http://openapi.baidu.com/public/2.0/bmt/translate?client_id=An28nhi0HTSWMlhMHqUZKBKy&q=today&from=auto&to=auto",null,callback);
    }

}
