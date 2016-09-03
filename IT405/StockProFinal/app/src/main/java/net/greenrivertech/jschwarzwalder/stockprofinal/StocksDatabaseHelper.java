package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eiseldora on 9/2/2016.
 */

public class StocksDatabaseHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_PURCHASES =
            "CREATE TABLE " + StocksContract.PurchaseEntry.TABLE_NAME + " (" +
                    StocksContract.PurchaseEntry._ID + " INTEGER PRIMARY KEY," +
                    StocksContract.PurchaseEntry.COLUMN_NAME_SYMBOL + TEXT_TYPE + COMMA_SEP +
                    StocksContract.PurchaseEntry.COLUMN_NAME_PRICE_AT_PURCHASE + INTEGER_TYPE + COMMA_SEP +
                    StocksContract.PurchaseEntry.COLUMN_NAME_DATE_PURCHASED + TEXT_TYPE + COMMA_SEP +
                    StocksContract.PurchaseEntry.COLUMN_NAME_AMOUNT_PURCHASED + INTEGER_TYPE +
                    " )";
    private static final String SQL_CREATE_STOCKS =
            "CREATE TABLE " + StocksContract.StockEntry.TABLE_NAME + " (" +
                    StocksContract.StockEntry._ID + " INTEGER PRIMARY KEY," +
                    StocksContract.StockEntry.COLUMN_NAME_SYMBOL + TEXT_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_CURRENT_PRICE + INTEGER_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_LAST_TRADED_DATE + TEXT_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_OPEN_PRICE + INTEGER_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_CLOSING_PRICE + INTEGER_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_DAY_HIGH + INTEGER_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_DAY_LOW + INTEGER_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_52_WEEK_RANGE + TEXT_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_CHANGE + INTEGER_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_VOLUME + INTEGER_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_CHANGE_PERCENT + TEXT_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_EARN_PER_SHARE + TEXT_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_PRICE_TO_EARNINGS + TEXT_TYPE + COMMA_SEP +
                    StocksContract.StockEntry.COLUMN_NAME_NAME + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_PURCHASES =
            "DROP TABLE IF EXISTS " + StocksContract.PurchaseEntry.TABLE_NAME;

    private static final String SQL_DELETE_STOCKS =
            "DROP TABLE IF EXISTS " + StocksContract.PurchaseEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public StocksDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_PURCHASES);
        db.execSQL(SQL_CREATE_STOCKS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_PURCHASES);
        db.execSQL(SQL_DELETE_STOCKS);
        onCreate(db);
    }

    public static void insertPurchase(SQLiteDatabase db, String symbol, int quantity,
                                      int purchasePrice, String date) {
        ContentValues purchaseValues = new ContentValues();
        purchaseValues.put(StocksContract.PurchaseEntry.COLUMN_NAME_SYMBOL, symbol);
        purchaseValues.put(StocksContract.PurchaseEntry.COLUMN_NAME_AMOUNT_PURCHASED, quantity);
        purchaseValues.put(StocksContract.PurchaseEntry.COLUMN_NAME_PRICE_AT_PURCHASE, purchasePrice);
        purchaseValues.put(StocksContract.PurchaseEntry.COLUMN_NAME_DATE_PURCHASED, date);
        db.insert(StocksContract.PurchaseEntry.TABLE_NAME, null, purchaseValues);
    }

    public static void insertOrUpdateStock(SQLiteDatabase db, String symbol, int price,
                                           String date, int openPrice, int closePrice, int high,
                                           int low, String yearRange, int change, int volume,
                                           String changePercent, String earningsPerShare,
                                           String priceToEarningsRatio, String name)
    {
        ContentValues stockValues = new ContentValues();
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_SYMBOL, symbol);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_CURRENT_PRICE, price);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_LAST_TRADED_DATE, date);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_OPEN_PRICE, openPrice);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_CLOSING_PRICE, closePrice);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_DAY_HIGH, high);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_DAY_LOW, low);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_52_WEEK_RANGE, yearRange);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_CHANGE, change);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_VOLUME, volume);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_CHANGE_PERCENT, changePercent);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_EARN_PER_SHARE, earningsPerShare);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_PRICE_TO_EARNINGS, priceToEarningsRatio);
        stockValues.put(StocksContract.StockEntry.COLUMN_NAME_NAME, name);

        Cursor cursor = db.query(StocksContract.StockEntry.TABLE_NAME, null,
                StocksContract.StockEntry.COLUMN_NAME_SYMBOL + " = ?", new String[]{symbol},
                null, null, null);
        if (cursor.getCount() == 0)
        {
            db.insert(StocksContract.StockEntry.TABLE_NAME, null, stockValues);
        }
        else
        {
            db.update(StocksContract.StockEntry.TABLE_NAME, stockValues,
                    StocksContract.StockEntry.COLUMN_NAME_SYMBOL + " = ?", new String[]{symbol});
        }
    }

}