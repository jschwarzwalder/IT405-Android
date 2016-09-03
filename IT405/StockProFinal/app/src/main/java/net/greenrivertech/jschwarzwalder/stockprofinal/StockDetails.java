package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

public class StockDetails extends AppCompatActivity {

    private String symbol;
    private String name;
    private String date;
    private int price;
    private int prevPrice;
    private int openPrice;
    private int change;
    private String changePercent;
    private int high;
    private int low;
    private int volume;
    private String yearRange;
    private String earningsPerShare;
    private String priceToEarningsRatio;

    private String datePurchased;
    private int priceWhenPurchased;
    private int quantityPurchased;
    private int purchaseID;


    private StockService stockService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_details);

        Bundle bundle = getIntent().getExtras();
        purchaseID = bundle.getInt("purcahseID");

        stockService = new StockService();

        try {
            SQLiteDatabase db = new StocksDatabaseHelper(this).getReadableDatabase();

            Cursor purchaseCursor = db.query(StocksContract.PurchaseEntry.TABLE_NAME, new String[]{
                            StocksContract.PurchaseEntry.COLUMN_NAME_AMOUNT_PURCHASED,
                            StocksContract.PurchaseEntry.COLUMN_NAME_DATE_PURCHASED,
                            StocksContract.PurchaseEntry.COLUMN_NAME_PRICE_AT_PURCHASE,
                            StocksContract.PurchaseEntry.COLUMN_NAME_SYMBOL,
                    },
                    "_ID=?", new String[]{Integer.toString(purchaseID)}, null, null, null);
            if (purchaseCursor.moveToFirst()) {
                quantityPurchased = purchaseCursor.getInt(0);
                datePurchased = purchaseCursor.getString(1);
                priceWhenPurchased = purchaseCursor.getInt(2);
                symbol = purchaseCursor.getString(3);
            }
            purchaseCursor.close();

            Cursor stockCursor = db.query(StocksContract.StockEntry.TABLE_NAME, new String[]{
                            StocksContract.StockEntry.COLUMN_NAME_CURRENT_PRICE,
                            StocksContract.StockEntry.COLUMN_NAME_LAST_TRADED_DATE,
                            StocksContract.StockEntry.COLUMN_NAME_OPEN_PRICE,
                            StocksContract.StockEntry.COLUMN_NAME_CLOSING_PRICE,
                            StocksContract.StockEntry.COLUMN_NAME_DAY_HIGH,
                            StocksContract.StockEntry.COLUMN_NAME_DAY_LOW,
                            StocksContract.StockEntry.COLUMN_NAME_52_WEEK_RANGE,
                            StocksContract.StockEntry.COLUMN_NAME_CHANGE,
                            StocksContract.StockEntry.COLUMN_NAME_VOLUME,
                            StocksContract.StockEntry.COLUMN_NAME_CHANGE_PERCENT,
                            StocksContract.StockEntry.COLUMN_NAME_EARN_PER_SHARE,
                            StocksContract.StockEntry.COLUMN_NAME_PRICE_TO_EARNINGS,
                            StocksContract.StockEntry.COLUMN_NAME_NAME,
                    },
                    StocksContract.StockEntry.COLUMN_NAME_SYMBOL + " = ?",
                    new String[]{symbol}, null, null, null);
            if (stockCursor.moveToFirst()) {
                price = stockCursor.getInt(0);
                date = stockCursor.getString(1);
                openPrice = stockCursor.getInt(2);
                prevPrice = stockCursor.getInt(3);
                high = stockCursor.getInt(4);
                low = stockCursor.getInt(5);
                yearRange = stockCursor.getString(6);
                change = stockCursor.getInt(7);
                volume = stockCursor.getInt(8);
                changePercent = stockCursor.getString(9);
                earningsPerShare = stockCursor.getString(10);
                priceToEarningsRatio = stockCursor.getString(11);
                name = stockCursor.getString(12);
            }
            purchaseCursor.close();


            db.close();
            updateStockInfo();

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void setFields(Bundle b) {
        symbol = b.getString("symbol");
        name = b.getString("name");
        date = b.getString("date");
        price = b.getInt("price");
        prevPrice = b.getInt("prevPrice");
        openPrice = b.getInt("openPrice");
        changePercent = b.getString("changePercent");
        change = b.getInt("change");
        changePercent = b.getString("changePercent");
        high = b.getInt("high");
        low = b.getInt("low");
        yearRange = b.getString("yearRange");
        volume = b.getInt("volume");
        earningsPerShare = b.getString("earningsPerShare");
        priceToEarningsRatio = b.getString("priceToEarningsRatio");

        datePurchased = b.getString("datePurchased");
        priceWhenPurchased = b.getInt("PriceWhenPurchased");
        quantityPurchased = b.getInt("quantityPurchased");


    }

    private void updateStockInfo() {
        TextView newSymbol = (TextView) findViewById(R.id.symbol);
        newSymbol.setText("SYMBOL: " + symbol);


        TextView newName = (TextView) findViewById(R.id.name);
        newName.setText(name);

        TextView datePurchasedText = (TextView) findViewById(R.id.datePurchased);
        datePurchasedText.setText(datePurchased);

        TextView priceWhenPurchasedText = (TextView) findViewById(R.id.priceWhenPurchased);
        priceWhenPurchasedText.setText(String.format("Current Price: %d.%02d", priceWhenPurchased / 100, priceWhenPurchased % 100));

        TextView quantityPurchasedTxte = (TextView) findViewById(R.id.quantityPurchased);
        quantityPurchasedTxte.setText(quantityPurchased);


        TextView newPrice = (TextView) findViewById(R.id.price);
        newPrice.setText(String.format("Current Price: %d.%02d", price / 100, price % 100));

        TextView lastPurchasedDate = (TextView) findViewById(R.id.lastPurchased);
        lastPurchasedDate.setText("Last Traded: " + date);

        TextView yesterdayPrice = (TextView) findViewById(R.id.prevDayClosePrice);
        yesterdayPrice.setText(String.format("Previous Day - Closing Price: %d.%02d", prevPrice / 100, prevPrice % 100));

        TextView openPriceText = (TextView) findViewById(R.id.priceAtOpening);

        openPriceText.setText(String.format("Price at Opening Today: %d.%02d", openPrice / 100, openPrice % 100));


        TextView newChange = (TextView) findViewById(R.id.change);
        TextView newChangePercent = (TextView) findViewById(R.id.changePercent);
        if (change >= 0) {
            newChange.setText(String.format("Change: +%d.%02d", change / 100, change % 100));
            newChange.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));

            newChangePercent.setText(String.format("Change: %s", changePercent));
            newChangePercent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));

        } else {
            newChange.setText(String.format("Change: -%d.%02d", change / 100, Math.abs(change % 100)));
            newChange.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));

            newChangePercent.setText(String.format("Change: %s", changePercent));
            newChangePercent.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));

        }

        TextView newHigh = (TextView) findViewById(R.id.high);
        newHigh.setText(String.format("Day High: %d.%02d", high / 100, high % 100));

        TextView newLow = (TextView) findViewById(R.id.low);
        newLow.setText(String.format("Day Low: %d.%02d", low / 100, low % 100));

        TextView newVolume = (TextView) findViewById(R.id.volume);
        newVolume.setText(String.format("Volume: %d", volume));


        TextView newYearRange = (TextView) findViewById(R.id.yearRange);
        newYearRange.setText("52 Week Range: " + yearRange);

        TextView earningsPerShareText = (TextView) findViewById(R.id.earningsPerShare);
        earningsPerShareText.setText(String.format("Earnings per Share: %s", earningsPerShare));

        TextView priceToEarningsRatioText = (TextView) findViewById(R.id.priceToEarningsRatio);
        priceToEarningsRatioText.setText(String.format("Price to Earnings: %s", priceToEarningsRatio));
    }

    public void onSearch(View v) {
        CharSequence symbol = ((TextView) findViewById(R.id.search)).getText();
        String symbolSearch = "";
        if (symbol.length() != 0) {
            symbolSearch = symbol.toString().toUpperCase();
            //Log.d(TAG, symbolSearch);
        } else {
            Toast.makeText(this, "Enter a Stock Symbol", Toast.LENGTH_SHORT).show();
            return;
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle b) {

        storeInBundle(b);
    }

    private void storeInBundle(Bundle b) {
        b.putString("symbol", symbol);
        b.putString("name", name);
        b.putString("date", date);
        b.putInt("price", price);
        b.putInt("prevPrice", prevPrice);
        b.putInt("openPrice", openPrice);
        b.putInt("change", change);
        b.putString("changePercent", changePercent);
        b.putInt("high", high);
        b.putInt("low", low);
        b.putString("yearRange", yearRange);
        b.putInt("volume", volume);
        b.putString("earningsPerShare", earningsPerShare);
        b.putString("priceToEarningsRatio", priceToEarningsRatio);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        setFields(savedInstanceState);

        updateStockInfo();
    }


    public void backtoList(View view) {
        finish();
    }

    public void onDelete(View view) {
        SQLiteDatabase db = new StocksDatabaseHelper(this).getWritableDatabase();
        db.delete(StocksContract.PurchaseEntry.TABLE_NAME, StocksContract.PurchaseEntry._ID + " = ?",
                new String[]{Integer.toString(purchaseID)});
    }

    public void onRefresh(View view) {
        new FetchStockInfoFromInternet().execute(symbol);
    }


    // Async Task Class
    class FetchStockInfoFromInternet extends AsyncTask<String, String, String> {

        List<HashMap<String, String>> nl;

        // Show Progress bar before fetching
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Shows Progress Bar Dialog and then call doInBackground method
//            prBar.setVisibility(View.VISIBLE);
//            lv.setVisibility(View.GONE);
        }


        @Override
        protected String doInBackground(String... stocks) {
            int count;
            try {
                String stocksToFetch = stocks[0].replace(" ", "+");

                nl = stockService.getStockInformation(stocksToFetch);

            } catch (Exception e) {
                Log.e("Error: ", e.getMessage());
            }
            return null;
        }


        // Once stock info has been downloaded

        @Override
        protected void onPostExecute(String stocks) {

//            prBar.setVisibility(View.GONE);
//            lv.setVisibility(View.VISIBLE);
            if ((nl != null) && (nl.size() > 0)) {
//                al.clear();
//                al.addAll(nl);
//                myAdapter.notifyDataSetChanged();
                HashMap<String, String> stock = nl.get(0);

                String quoteError = stock.get(Constants.KEY_QUOTE_ERROR);
                if (quoteError != null && quoteError.equals("true")) {

                    Toast.makeText(getBaseContext(), "Server Error: Please try again later.",
                            Toast.LENGTH_SHORT).show();
                } else {
                    findViewById(R.id.symbol).setVisibility(View.VISIBLE);
                    symbol = stock.get(Constants.KEY_SYMBOL);

                    findViewById(R.id.name).setVisibility(View.VISIBLE);
                    name = stock.get(Constants.KEY_NAME);

                    findViewById(R.id.price).setVisibility(View.VISIBLE);
                    price = (int) (Double.parseDouble(stock.get(Constants.KEY_CURPRICE)) * 100);

                    findViewById(R.id.prevDayClosePrice).setVisibility(View.VISIBLE);
                    prevPrice = (int) (Double.parseDouble(stock.get(Constants.KEY_PREVCLOSE)) * 100);

                    findViewById(R.id.priceAtOpening).setVisibility(View.VISIBLE);
                    openPrice = (int) (Double.parseDouble(stock.get(Constants.KEY_OPENPRICE)) * 100);

                    findViewById(R.id.lastPurchased).setVisibility(View.VISIBLE);
                    date = stock.get(Constants.KEY_DATE);

                    findViewById(R.id.change).setVisibility(View.VISIBLE);
                    change = (int) (Double.parseDouble(stock.get(Constants.KEY_CHANGE)) * 100);

                    findViewById(R.id.changePercent).setVisibility(View.VISIBLE);
                    changePercent = stock.get(Constants.KEY_PRCNTCHANGE);

                    findViewById(R.id.high).setVisibility(View.VISIBLE);
                    high = (int) (Double.parseDouble(stock.get(Constants.KEY_HIGHPRICE)) * 100);

                    findViewById(R.id.low).setVisibility(View.VISIBLE);
                    low = (int) (Double.parseDouble(stock.get(Constants.KEY_LOWPRICE)) * 100);

                    findViewById(R.id.yearRange).setVisibility(View.VISIBLE);
                    yearRange = stock.get(Constants.KEY_ANNRANGE);

                    findViewById(R.id.earningsPerShare).setVisibility(View.VISIBLE);
                    earningsPerShare = stock.get(Constants.KEY_EARNING);

                    findViewById(R.id.priceToEarningsRatio).setVisibility(View.VISIBLE);
                    priceToEarningsRatio = stock.get(Constants.KEY_PE);

                    findViewById(R.id.volume).setVisibility(View.VISIBLE);
                    volume = Integer.parseInt(stock.get(Constants.KEY_VOLUME));

                    findViewById(R.id.qty).setVisibility(View.VISIBLE);
                    findViewById(R.id.addButton).setVisibility(View.VISIBLE);

                    updateStockInfo();
                    new UpdateDatabase().execute();
                }

            } else {

                findViewById(R.id.symbol).setVisibility(View.INVISIBLE);
                findViewById(R.id.name).setVisibility(View.INVISIBLE);
                findViewById(R.id.price).setVisibility(View.INVISIBLE);
                findViewById(R.id.prevDayClosePrice).setVisibility(View.INVISIBLE);
                findViewById(R.id.priceAtOpening).setVisibility(View.INVISIBLE);
                findViewById(R.id.lastPurchased).setVisibility(View.INVISIBLE);
                findViewById(R.id.change).setVisibility(View.INVISIBLE);
                findViewById(R.id.changePercent).setVisibility(View.INVISIBLE);
                findViewById(R.id.high).setVisibility(View.INVISIBLE);
                findViewById(R.id.low).setVisibility(View.INVISIBLE);
                findViewById(R.id.yearRange).setVisibility(View.INVISIBLE);
                findViewById(R.id.earningsPerShare).setVisibility(View.INVISIBLE);
                findViewById(R.id.priceToEarningsRatio).setVisibility(View.INVISIBLE);
                findViewById(R.id.volume).setVisibility(View.INVISIBLE);
                findViewById(R.id.qty).setVisibility(View.INVISIBLE);
                findViewById(R.id.addButton).setVisibility(View.INVISIBLE);
                Toast.makeText(getBaseContext(), "No Stock Information Found",
                        Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class UpdateDatabase extends AsyncTask<Void, Void, Void> {
        ContentValues values = new ContentValues();
        SQLiteDatabase db;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            values.put(StocksContract.StockEntry.COLUMN_NAME_SYMBOL, symbol);
            values.put(StocksContract.StockEntry.COLUMN_NAME_CURRENT_PRICE, price);
            values.put(StocksContract.StockEntry.COLUMN_NAME_LAST_TRADED_DATE, date);
            values.put(StocksContract.StockEntry.COLUMN_NAME_OPEN_PRICE, openPrice);
            values.put(StocksContract.StockEntry.COLUMN_NAME_CLOSING_PRICE, prevPrice);
            values.put(StocksContract.StockEntry.COLUMN_NAME_DAY_HIGH, high);
            values.put(StocksContract.StockEntry.COLUMN_NAME_DAY_LOW, low);
            values.put(StocksContract.StockEntry.COLUMN_NAME_52_WEEK_RANGE, yearRange);
            values.put(StocksContract.StockEntry.COLUMN_NAME_CHANGE, change);
            values.put(StocksContract.StockEntry.COLUMN_NAME_VOLUME, volume);
            values.put(StocksContract.StockEntry.COLUMN_NAME_CHANGE_PERCENT, changePercent);
            values.put(StocksContract.StockEntry.COLUMN_NAME_EARN_PER_SHARE, earningsPerShare);
            values.put(StocksContract.StockEntry.COLUMN_NAME_PRICE_TO_EARNINGS, priceToEarningsRatio);
            values.put(StocksContract.StockEntry.COLUMN_NAME_NAME, name);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            db = new StocksDatabaseHelper(StockDetails.this).getReadableDatabase();
            db.update(StocksContract.StockEntry.TABLE_NAME, values,
                    StocksContract.StockEntry.COLUMN_NAME_SYMBOL + " = ?", new String[]{symbol});
            return null;
        }
    }
}
