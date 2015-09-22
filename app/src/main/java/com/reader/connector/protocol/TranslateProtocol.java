package com.reader.connector.protocol;

import com.reader.conf.Config;
import com.reader.connector.ConnectorManage;
import com.reader.connector.HttpCallBack;

import java.util.LinkedHashMap;

/**
 * Created by Administrator on 2015/9/20.
 */
public class TranslateProtocol {

    private static long TranslateProtocol(String url,
                                          LinkedHashMap<String, Object> map, HttpCallBack callback) {
        return ConnectorManage.getInstance().Post(url, map, callback);
    }

    private static long TranslateProtocolGet(String url, HttpCallBack callback
    ) {
        return ConnectorManage.getInstance().Get(url, callback);
    }
    private static long TranslateProtocolXml(String url, LinkedHashMap<String, Object> map,HttpCallBack callback
    ) {
        return ConnectorManage.getInstance().xmlPost(url, map, callback);
    }

    //voa翻译接口
    public static long voaTransLate(String word, HttpCallBack callBack) {
        LinkedHashMap < String, Object > entity = new LinkedHashMap<String, Object>();
        entity.put("q", word);
        String url = Config.voaWordApi +word;
        return TranslateProtocolXml("http://word.iyuba.com/words/apiWord.jsp", entity, callBack);
    }
    //有道翻译接口
    public static long youdaoTransLate(String word, HttpCallBack callBack) {
        LinkedHashMap < String, Object > entity = new LinkedHashMap<String, Object>();
        entity.put("q", word);
        String url = Config.voaWordApi +word;

        return TranslateProtocolXml(Config.youdaoWordApi, entity, callBack);

    }


    //百度翻译接口
    public static long bdTransLate(String word, HttpCallBack callBack) {
        LinkedHashMap < String, Object > entity = new LinkedHashMap<String, Object>();
        entity.put("q", word);
        entity.put("client_id", Config.Baidu_key);
        entity.put("from", "auto");
        entity.put("to", "auto");
        return TranslateProtocol(Config.baiduWordApi, entity, callBack);
    }
}
