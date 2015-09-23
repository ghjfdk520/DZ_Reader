package com.reader.conf;


import com.apkfuns.logutils.LogUtils;

/**
 * Created by Administrator on 2015/9/9.
 */
public class Config {
    public static boolean DEBUG = true;

    public final static String TAG ="DONG_Z";

    public final static String Baidu_key = "An28nhi0HTSWMlhMHqUZKBKy"; //百度翻译key

    //英语单词翻译接口 http://word.iyuba.com/words/apiWord.jsp?q=hello"
    public static String voaWordApi = "http://word.iyuba.com/words/apiWord.jsp?q=";

    //百度翻译接口 http://openapi.baidu.com/public/2.0/bmt/translate?client_id=An28nhi0HTSWMlhMHqUZKBKy&q=today&from=auto&to=auto http://openapi.baidu.com/public/2.0/bmt/translate?client_id=An28nhi0HTSWMlhMHqUZKBKy&q=today&from=auto&to=auto
    public static String baiduWordApi = "http://openapi.baidu.com/public/2.0/bmt/translate";


    //有道翻译接口 http://fanyi.youdao.com/openapi.do?keyfrom=fanyiweb&type=selector&version=1.2&select=off&translate=on"
    public static String youdaoWordApi = "http://fanyi.youdao.com/appapi/translate";
    //有道翻译发音接口 http://dict.youdao.com/dictvoice?audio=translate&le=eng
    public static String youdaoVoice = "http://dict.youdao.com/dictvoice?le=eng&audio=";
    static {
        LogUtils.configTagPrefix = TAG;
    }


}
