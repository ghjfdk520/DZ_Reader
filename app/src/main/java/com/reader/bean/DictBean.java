package com.reader.bean;

import com.reader.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DongZ on 2015/9/23 0023.
 */
public class DictBean {

    public Dict dict;
    public Translate fanyi;
    public List<Translate> sentence = new ArrayList<>();
    public static class  Translate{
        public   String trans;
        public   String org;
    }
    public static class Dict{
        public String phonetic;
        public String phrases;
        public String org;


        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getPhrases() {
            return phrases;
        }

        public void setPhrases(String phrases) {
            this.phrases = phrases;
        }

        public String getOrg() {
            return org;
        }

        public void setOrg(String org) {
            this.org = org;
        }

        @Override
        public String toString() {
            return "Dict{" +
                    "phonetic='" + phonetic + '\'' +
                    ", phrases='" + phrases + '\'' +
                    ", org='" + org + '\'' +
                    '}';
        }
    }


    public String sentenceFormat(){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < sentence.size(); i++) {
            Translate translate = sentence.get(i);
            stringBuffer.append(i);
            stringBuffer.append(translate.org);
            stringBuffer.append("#");
            stringBuffer.append(translate.trans);
            stringBuffer.append("#");
        }
        return stringBuffer.toString();
    }


    public void sentenceParse(String sentenceStr){
        String sentenceTemp[] = sentenceStr.split("#");
        sentence.clear();
        for (int i = 0; i < sentenceTemp.length; i+=2) {

            if(Utils.isEmptyOrNullStr(sentenceTemp[i]))
                return;
            Translate translate = new Translate();
            translate.org = sentenceTemp[i];
            translate.trans = sentenceTemp[i++];
            sentence.add(translate);
        }
    }

    @Override
    public String toString() {
        return "DictBean{" +
                "dict=" + dict.toString() +
                '}';
    }
}

