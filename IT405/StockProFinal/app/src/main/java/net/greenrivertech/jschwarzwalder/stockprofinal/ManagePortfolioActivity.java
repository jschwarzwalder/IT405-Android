package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class ManagePortfolioActivity extends AppCompatActivity {


    final static int MY_ID = 434;

    private final static String TAG = ManagePortfolioActivity.class.getSimpleName();

    private static final String[] fromList = new String[]{

            StocksContract.PurchaseEntry.COLUMN_NAME_SYMBOL,
            StocksContract.PurchaseEntry.COLUMN_NAME_PRICE_AT_PURCHASE,
            StocksContract.PurchaseEntry.COLUMN_NAME_DATE_PURCHASED,
            StocksContract.PurchaseEntry.COLUMN_NAME_AMOUNT_PURCHASED,
    };
    private static final int[] toList = new int[]{

            R.id.symbol,
            R.id.Price,
            R.id.datePurchased,
            R.id.Quantity,
    };

    private CursorAdapter stockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_portfolio);



        Cursor cursor = queryPurchases();

        final ListView stocksList = (ListView) findViewById(R.id.stockListDisplay);

        stockAdapter = new StockCursorAdapter(this, R.layout.stock_list_item,
                cursor, fromList, toList, 0);

        stocksList.setAdapter(stockAdapter);
        stocksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                showPurchase(view, (int) id);
            }
        });

        //update Total Value and refresh the display
    }

    private Cursor queryPurchases() {
        SQLiteDatabase db = new StocksDatabaseHelper(this).getReadableDatabase();
        return db.query(StocksContract.PurchaseEntry.TABLE_NAME,
                new String[]{StocksContract.PurchaseEntry._ID,
                        StocksContract.PurchaseEntry.COLUMN_NAME_SYMBOL,
                        StocksContract.PurchaseEntry.COLUMN_NAME_PRICE_AT_PURCHASE,
                        StocksContract.PurchaseEntry.COLUMN_NAME_DATE_PURCHASED,
                        StocksContract.PurchaseEntry.COLUMN_NAME_AMOUNT_PURCHASED,


                }, null, null, null, null, null);
    }

    protected void onResume(Bundle savedInstanceState) {


        stockAdapter.swapCursor(queryPurchases());
    }


    public void onDelete(View view) {
        int purchaseID = (Integer) view.getTag();
        SQLiteDatabase db = new StocksDatabaseHelper(this).getWritableDatabase();
        db.delete(StocksContract.PurchaseEntry.TABLE_NAME, StocksContract.PurchaseEntry._ID + " = ?",
                new String[]{Integer.toString(purchaseID)});
        stockAdapter.swapCursor(queryPurchases());
    }


    public void showPurchase(View view, int purchaseID) {
        Intent i = new Intent(this, StockDetails.class);
        Bundle b = new Bundle();
        b.putInt("purchaseID", purchaseID);

        i.putExtras(b);
        startActivityForResult(i, MY_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {



        if ((requestCode == MY_ID) && (resultCode == RESULT_OK)) {

            // check for data that may have returned from the second activity

            stockAdapter.swapCursor(queryPurchases());
        }

        super.onActivityResult(requestCode, resultCode, i);
    }

    public void showStockQuotes(View view) {
        Intent i = new Intent(this, StockQuotesActivity.class);
        startActivity(i);
    }

    private class StockCursorAdapter extends SimpleCursorAdapter {

        public StockCursorAdapter(Context context, int layoutID, Cursor cursor, String[] fromList,
                                  int[] toList, int flags)
        {
            super(context, layoutID, cursor, fromList, toList, flags);

        }

        @Override
        public View newView(Context _context, Cursor _cursor, ViewGroup parent) {
            LayoutInflater inflater =
                    (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.stock_list_item, parent, false);
            return view;
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            super.bindView(view, context, cursor);
            int id = cursor.getInt(cursor.getColumnIndex(StocksContract.PurchaseEntry._ID));
            Button button = (Button) view.findViewById(R.id.Delete);
            button.setTag(id);

        }
    }
}
