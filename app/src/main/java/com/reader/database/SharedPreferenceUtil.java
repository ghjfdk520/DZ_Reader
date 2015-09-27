package com.reader.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/**
 * Created by DongZ on 2015/9/24.
 */
public class SharedPreferenceUtil {

    private static String LOADDATABASE = "LoadDatabase";
    private static String HISTORYQUERY ="HistoryQuery";
    private static SharedPreferenceUtil sharedPreferenceUtil;
    private static SharedPreferences sharedPreferences;
    private final static String KEY = "meyou_sharepreferences";

    /** 统计iaroundid **/
    public final static String UMS_HEART = "ums_heart";
    private String SAVE_PUBLIC_KEY = "snPE6LHB8S8GLN96";
    public final static String DEVICEID = "device_id"; // 设备唯一码


    /** 关于通知设置-声音和震动 Start 需要加上用户id使用 */

    // [免打扰设置]类型：boolean
    public final static String DND_SETTING = "dnd_setting";
    // [免打扰起始]类型：int
    public final static String REC_START_TIME = "rec_start_time";
    // [免打扰结束]类型：int
    public final static String REC_END_TIME = "rec_end_time";

    private SharedPreferenceUtil(Context context) {
        sharedPreferences = context.getSharedPreferences(KEY, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance(Context context) {
        if (sharedPreferenceUtil == null) {
            synchronized (SharedPreferenceUtil.class) {

                if (sharedPreferenceUtil == null) {
                    sharedPreferenceUtil = new SharedPreferenceUtil(context);
                }
            }

        }
        return sharedPreferenceUtil;
    }

    /**
     * 设置String类型值
     *
     * @param key
     * @param value
     */
    public void putString( String key , String value )
    {
        Editor editor = sharedPreferences.edit( );
        editor.putString( key , value );
        editor.commit( );
    }

    /**
     * 设置long类型值
     *
     * @param key
     * @param value
     */
    public void putLong( String key , long value )
    {
        Editor editor = sharedPreferences.edit( );
        editor.putLong( key , value );
        editor.commit( );
    }

    /**
     * 设置int类型值
     *
     * @param key
     * @param value
     */
    public void putInt( String key , int value )
    {
        Editor editor = sharedPreferences.edit( );
        editor.putInt( key , value );
        editor.commit( );
    }

    /**
     * 设置Boolean类型值
     *
     * @param key
     * @param value
     */
    public void putBoolean( String key , boolean value )
    {
        Editor editor = sharedPreferences.edit( );
        editor.putBoolean( key , value );
        editor.commit( );
    }

    /**
     * 设置Float类型值
     *
     * @param key
     * @param value
     */
    public void putFloat( String key , float value )
    {
        SharedPreferences.Editor editor = sharedPreferences.edit( );
        editor.putFloat( key , value );
        editor.commit( );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为""
     *
     * @param key
     * @return
     */
    public String getString( String key )
    {
        return getString( key , "" );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为""
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public String getString( String key , String defaultValue )
    {
        return sharedPreferences.getString( key , defaultValue );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为false
     *
     * @param key
     * @return
     */
    public boolean getBoolean( String key )
    {
        return getBoolean( key , false );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为false
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public boolean getBoolean( String key , boolean defaultValue )
    {
        return sharedPreferences.getBoolean( key , defaultValue );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为0
     *
     * @param key
     * @return
     */
    public int getInt( String key )
    {
        return getInt( key , 0 );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为0
     *
     * @param key
     * @return
     */
    public int getInt( String key , int defaultValue )
    {
        return sharedPreferences.getInt( key , defaultValue );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为0
     *
     * @param key
     * @return
     */
    public long getLong( String key )
    {
        return getLong( key , 0L );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为0
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public long getLong( String key , Long defaultValue )
    {
        return sharedPreferences.getLong( key , defaultValue );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为0
     *
     * @param key
     * @return
     */
    public float getFloat( String key )
    {
        return getFloat( key , 0f );
    }

    /**
     * 获取key相对应的value，如果不设默认参数，默认值为0
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public float getFloat( String key , Float defaultValue )
    {
        return sharedPreferences.getFloat( key , defaultValue );
    }

    /** 判断是否存在此字段 */
    public boolean contains( String key )
    {
        return sharedPreferences.contains( key );
    }

    /** 判断是否存在此字段 */
    public boolean has( String key )
    {
        return sharedPreferences.contains( key );
    }

    /** 删除sharedPreferences文件中对应的Key和value */
    public boolean remove( String key )
    {
        SharedPreferences.Editor editor = sharedPreferences.edit( );
        editor.remove( key );
        return editor.commit( );
    }
}
