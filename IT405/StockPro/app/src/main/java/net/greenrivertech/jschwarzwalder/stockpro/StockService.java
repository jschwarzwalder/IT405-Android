package net.greenrivertech.jschwarzwalder.stockpro;

import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jsingh on 8/18/16.
 */
public class StockService {

    final static String TAG = StockService.class.getSimpleName();

    final static String baseURL = "https://ws.cdyne.com/delayedstockquote/delayedstockquote.asmx/GetQuote?LicenseKey=0&StockSymbol=";

    //https://ws.cdyne.com/delayedstockquote/delayedstockquote.asmx/GetQuoteDataSet?StockSymbols=msft,amzn&LicenseKey=0
    //final static String baseURL = "https://ws.cdyne.com/delayedstockquote/delayedstockquote.asmx/GetQuoteDataSet?LicenseKey=0&StockSymbols=";


    public List<HashMap<String, String>> getStockInformation(String mStock) {
        try {

            String url = baseURL + mStock;
            Log.d(TAG, "fetching ..." + url);
            return loadXmlFromNetwork(baseURL + mStock);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    private List<HashMap<String, String>> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException {


        InputStream stream = null;
        List<HashMap<String, String>> entries;

        // Instantiate the parser
        StockXMLParser XmlParser = new StockXMLParser();

        try {
            stream = downloadUrl(urlString);
            entries = XmlParser.parse(stream);
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return entries;
    }

    // Given a string representation of a URL, sets up a connection and gets
// an input stream.
    private InputStream downloadUrl(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        return conn.getInputStream();
    }


}
