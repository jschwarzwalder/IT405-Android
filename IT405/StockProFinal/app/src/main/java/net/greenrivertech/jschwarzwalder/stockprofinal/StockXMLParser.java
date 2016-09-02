package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jsingh on 8/18/16.
 */
public class StockXMLParser {

    // We don't use namespaces
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        } finally {
            in.close();
        }
    }


    private List readFeed(XmlPullParser parser) throws XmlPullParserException, IOException {
        List entries = new ArrayList();
        parser.require(XmlPullParser.START_TAG, ns, Constants.KEY_ITEM);
        entries.add(readEntry(parser));
        return entries;
    }




    // Parses the contents
    private HashMap<String, String> readEntry(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, Constants.KEY_ITEM);

        String symbol = null;
        String name = null;
        String lastprice = null;
        String date = null;
        String change = null;
        String opening = null;
        String highprice = null;
        String lowprice = null;
        String volume = null;
        String prevclose = null;
        String percentchange = null;
        String annrange = null;
        String earning = null;
        String pe = null;


        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String elname = parser.getName();

            if (elname.equals(Constants.KEY_SYMBOL)) {
                symbol = readElement(parser, Constants.KEY_SYMBOL);
            } else if (elname.equals(Constants.KEY_NAME)) {
                name = readElement(parser, Constants.KEY_NAME);
            } else if (elname.equals(Constants.KEY_CURPRICE)) {
                lastprice = readElement(parser, Constants.KEY_CURPRICE);
            } else if (elname.equals(Constants.KEY_DATE)) {
                date = readElement(parser, Constants.KEY_DATE);
            } else if (elname.equals(Constants.KEY_CHANGE)) {
                change = readElement(parser, Constants.KEY_CHANGE);
            } else if (elname.equals(Constants.KEY_OPENPRICE)) {
                opening = readElement(parser, Constants.KEY_OPENPRICE);
            } else if (elname.equals(Constants.KEY_HIGHPRICE)) {
                highprice = readElement(parser, Constants.KEY_HIGHPRICE);
            } else if (elname.equals(Constants.KEY_LOWPRICE)) {
                lowprice = readElement(parser, Constants.KEY_LOWPRICE);
            } else if (elname.equals(Constants.KEY_VOLUME)) {
                volume = readElement(parser, Constants.KEY_VOLUME);
            } else if (elname.equals(Constants.KEY_PREVCLOSE)) {
                prevclose = readElement(parser, Constants.KEY_PREVCLOSE);
            } else if (elname.equals(Constants.KEY_PRCNTCHANGE)) {
                percentchange = readElement(parser, Constants.KEY_PRCNTCHANGE);
            } else if (elname.equals(Constants.KEY_ANNRANGE)) {
                annrange = readElement(parser, Constants.KEY_ANNRANGE);
            } else if (elname.equals(Constants.KEY_EARNING)) {
                earning = readElement(parser, Constants.KEY_EARNING);
            } else if (elname.equals(Constants.KEY_PE)) {
                pe = readElement(parser, Constants.KEY_PE);
            } else {
                skip(parser);
            }
        }
        return createStock(symbol, name, lastprice, date,
                change, opening, highprice, lowprice, volume,
                prevclose, percentchange, annrange,
                earning, pe);


    }


    // Processes title tags in the feed.
    private String readElement(XmlPullParser parser, String elname) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, elname);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, elname);
        return title;
    }


    // For the appropriate tags, extracts their text values.
    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    //skip when we do not care about the tag
    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


    private HashMap<String, String> createStock(String symbol, String name,
                                                String lastprice, String date, String change,
                                                String opening, String highprice, String lowprice, String volume,
                                                String prevclose, String percentchange,
                                                String annrange, String earning, String pe) {
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put(Constants.KEY_PE, pe);
        hm.put(Constants.KEY_EARNING, earning);
        hm.put(Constants.KEY_ANNRANGE, annrange);
        hm.put(Constants.KEY_PRCNTCHANGE, percentchange);

        hm.put(Constants.KEY_PREVCLOSE, prevclose);
        hm.put(Constants.KEY_VOLUME, volume);
        hm.put(Constants.KEY_LOWPRICE, lowprice);
        hm.put(Constants.KEY_HIGHPRICE, highprice);
        hm.put(Constants.KEY_OPENPRICE, opening);
        hm.put(Constants.KEY_CHANGE, change);
        hm.put(Constants.KEY_DATE, date);
        hm.put(Constants.KEY_CURPRICE, lastprice);
        hm.put(Constants.KEY_NAME, name);
        hm.put(Constants.KEY_SYMBOL, symbol);

        return hm;
    }

}
