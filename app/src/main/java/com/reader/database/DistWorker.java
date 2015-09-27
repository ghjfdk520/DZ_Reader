package com.reader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.apkfuns.logutils.LogUtils;
import com.reader.bean.DictBean;

/**
 * Created by DongZ on 2015/9/25 0025.
 */
public class DistWorker extends ITableWorker{
    public static final String TB_NAME = "word"; // 表名
    public static final String LOC_ID = "loc_id";
    public static final String ORG = "org";
    public static final String PHONETIC = "phonetic";
    public static final String PHRASES = "phrases";
    public static final String SENTENCE = "sentence";
    public static final String SOUND = "sound";
    public static final String WORDID = "word_id";

    public static final String[] selectors = {
            ORG, PHONETIC, PHRASES, SENTENCE, SOUND
    };
    public static final String deleteAll = "DELETE FROM TB_NAME";

    public static final String tableSql = "CREATE TABLE IF NOT EXISTS " + TB_NAME + " ( " +
            WORDID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ORG + " TEXT," +
            PHONETIC + " TEXT," +
            PHRASES + " TEXT," +
            SENTENCE + " TEXT," +
            SOUND + " TEXT);";

    public DistWorker(Context context) {
        super( context , LOC_ID , TB_NAME );
    }

    public long addDict(DictBean dictBean){
        ContentValues values = new ContentValues( );
        values.put(ORG,dictBean.dict.org);
        values.put(PHONETIC,dictBean.dict.getPhonetic());
        values.put(PHRASES,dictBean.dict.getPhrases());
        values.put(SENTENCE, dictBean.dict.org);
        return onInsert(values);
    }


    public DictBean getDict(){

        String  where =  ORG + " = 'a'"  ;
        Cursor cursor = onSelect(selectors,where);
        DictBean dictBean = new DictBean();
        if(cursor.moveToFirst()){
//            dictBean.dict.setOrg(cursor.getString(cursor.getColumnIndex(ORG)));
//            dictBean.dict.setPhonetic(cursor.getString(cursor.getColumnIndex(PHONETIC)));
//            dictBean.dict.setPhrases(cursor.getString(cursor.getColumnIndex(PHRASES)));
//            dictBean.sentenceParse(cursor.getString(cursor.getColumnIndex(SENTENCE)));
//            dictBean.sentenceParse(cursor.getString(cursor.getColumnIndex(SOUND)));
            LogUtils.e(cursor.getString( cursor.getColumnIndex(ORG))+"  "+cursor.getString(cursor.getColumnIndex(PHRASES))+"  "+cursor.getString(cursor.getColumnIndex(PHONETIC))+"  "
            +cursor.getString(cursor.getColumnIndex(SENTENCE))+"  "

            );
        }
        return dictBean;
    }

}
