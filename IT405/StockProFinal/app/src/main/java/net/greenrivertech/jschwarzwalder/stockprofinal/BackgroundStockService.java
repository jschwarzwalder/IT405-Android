package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;

import java.util.HashMap;
import java.util.List;

public class BackgroundStockService extends Service {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(updateDatabase);
    }

    private Runnable updateDatabase = new Runnable() {
        public void run() {
            // Do something here
            SQLiteDatabase db = new StocksDatabaseHelper(context).getWritableDatabase();
            StockService stockService = new StockService();
            Cursor cursor = db.query(StocksContract.StockEntry.TABLE_NAME,
                    new String[]{StocksContract.StockEntry.COLUMN_NAME_SYMBOL},
                    null, null, null, null, null);
            if (cursor.moveToFirst())
            {
                do {
                    String symbol = cursor.getString(0);
                    List<HashMap<String, String>> stockList
                            = stockService.getStockInformation(symbol);
                    if (stockList != null && stockList.size() > 0)
                    {
                        HashMap<String, String> stock = stockList.get(0);

                        String name = stock.get(Constants.KEY_NAME);
                        int price = (int) (Double.parseDouble(stock.get(Constants.KEY_CURPRICE)) * 100);
                        int prevPrice = (int) (Double.parseDouble(stock.get(Constants.KEY_PREVCLOSE)) * 100);
                        int openPrice = (int) (Double.parseDouble(stock.get(Constants.KEY_OPENPRICE)) * 100);
                        String date = stock.get(Constants.KEY_DATE);
                        int change = (int) (Double.parseDouble(stock.get(Constants.KEY_CHANGE)) * 100);
                        String changePercent = stock.get(Constants.KEY_PRCNTCHANGE);
                        int high = (int) (Double.parseDouble(stock.get(Constants.KEY_HIGHPRICE)) * 100);
                        int low = (int) (Double.parseDouble(stock.get(Constants.KEY_LOWPRICE)) * 100);
                        String yearRange = stock.get(Constants.KEY_ANNRANGE);
                        String earningsPerShare = stock.get(Constants.KEY_EARNING);
                        String priceToEarningsRatio = stock.get(Constants.KEY_PE);
                        int volume = Integer.parseInt(stock.get(Constants.KEY_VOLUME));

                        StocksDatabaseHelper.insertOrUpdateStock(db, symbol, price, date, openPrice,
                                prevPrice, high, low, yearRange, change, volume, changePercent,
                                earningsPerShare, priceToEarningsRatio, name);
                    }
                } while(cursor.moveToNext());
            }

            stopSelf();
        }
    };

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

}