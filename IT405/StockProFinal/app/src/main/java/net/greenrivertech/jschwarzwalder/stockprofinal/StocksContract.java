package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Eiseldora on 9/2/2016.
 */
public final class StocksContract {// To prevent someone from accidentally instantiating the contract class,

    // make the constructor private.
    private StocksContract() {
    }

    /* Inner class that defines the table contents */
    public static class PurchaseEntry implements BaseColumns {
        public static final String TABLE_NAME = "stocksPurchased";
        public static final String COLUMN_NAME_SYMBOL = "symbol";
        public static final String COLUMN_NAME_PRICE_AT_PURCHASE = "priceAtPurchase";
        public static final String COLUMN_NAME_DATE_PURCHASED = "datePurchased";
        public static final String COLUMN_NAME_AMOUNT_PURCHASED = "amountPurchased";
    }

    /* Inner class that defines the table contents */
    public static class StockEntry implements BaseColumns {
        public static final String TABLE_NAME = "stocks";
        public static final String COLUMN_NAME_SYMBOL = "symbol";
        public static final String COLUMN_NAME_CURRENT_PRICE = "currentPrice";
        public static final String COLUMN_NAME_LAST_TRADED_DATE = "lastTraded";
        public static final String COLUMN_NAME_OPEN_PRICE = "openingPrice";
        public static final String COLUMN_NAME_CLOSING_PRICE = "closingPrice";
        public static final String COLUMN_NAME_DAY_HIGH = "dayHigh";
        public static final String COLUMN_NAME_DAY_LOW= "dayLow";
        public static final String COLUMN_NAME_52_WEEK_RANGE = "YearRange";
        public static final String COLUMN_NAME_CHANGE = "change";
        public static final String COLUMN_NAME_VOLUME = "volume";
        public static final String COLUMN_NAME_CHANGE_PERCENT= "changePercent";
        public static final String COLUMN_NAME_EARN_PER_SHARE = "earningsPerShare";
        public static final String COLUMN_NAME_PRICE_TO_EARNINGS = "priceToEarnings";
        public static final String COLUMN_NAME_NAME = "name";
    }
}