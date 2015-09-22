package com.reader.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/9/8.
 */
public class RegEXP {
    private Pattern compiler;
    String regex = "/s(.+?)/s";
    private volatile  static RegEXP instance;
    private RegEXP (){
    }
    public static RegEXP getInstance() {
        if (instance == null) {
            synchronized(RegEXP.class) {
                if(instance == null)
                   instance = new RegEXP();
            }
        }
        return instance;
    }

    public String regExpStr(String str){
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(str);
        while(matcher.find()) {
            return matcher.group();
        }
        return "";
    }
}
