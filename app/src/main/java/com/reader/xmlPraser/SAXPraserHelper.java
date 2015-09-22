package com.reader.xmlPraser;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Administrator on 2015/9/20.
 */
public class SAXPraserHelper extends DefaultHandler{

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }


}
