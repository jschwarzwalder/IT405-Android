package net.greenrivertech.jschwarzwalder.stockpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    boolean displayMore = true;

    final static int MY_ID = 434;

    private final static String TAG = MainActivity.class.getSimpleName();

    HashMap<String, Stock> stocks = new HashMap<String, Stock>();
    ArrayList<Stock> stocksDisplay = new ArrayList<Stock>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_portfolio);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);

        Stock amz = new Stock("AMZN",
                getString(R.string.amznName),
                46432, //Current Price in Pennies
                329, //Change in Pennies
                67232, // High
                37223, // Low
                3200057, // Volume
                R.string.amzndesc);

        Stock msft = new Stock("MSFT",
                getString(R.string.msftName),
                6267, //Current Price in Pennies
                -29, //Change in Pennies
                7256, // High
                5432, // Low
                24565612, // Volume
                R.string.msftdesc);

        Stock jpy = new Stock("JPY",
                getString(R.string.nintName),
                2322000, //Current Price in Pennies
                500000, //Change in Pennies
                2822000, // High
                1058634, // Low
                123456789, // Volume
                R.string.nintdesc);

        Stock leaf = new Stock("LEAF",
                getString(R.string.leafName),
                4868, //Current Price in Pennies
                -46, //Change in Pennies
                6725, // High
                3358, // Low
                4556879, // Volume
                R.string.leafdesc);

        Stock netf = new Stock("NFLX",
                getString(R.string.netfName),
                8589, //Current Price in Pennies
                -10, //Change in Pennies
                14635, // High
                8589, // Low
                54682317, // Volume
                R.string.netfdesc);

        stocks.put("AMZN", amz);
        stocks.put("MSFT", msft);
        stocks.put("JPY", jpy);
        stocks.put("LEAF", leaf);
        stocks.put("NFLX", netf);



    }

    protected void onResume(Bundle savedInstanceState) {


    }

    public void onAdd(View v) {
        CharSequence symbol = ((TextView) findViewById(R.id.search)).getText();
        String symbolSearch = "";
        if (symbol.length() != 0) {
            symbolSearch = symbol.toString().toUpperCase();
            //Log.d(TAG, symbolSearch);
        } else {
            Toast.makeText(this, "Enter a Stock Symbol", Toast.LENGTH_SHORT).show();
            return;
        }
        Stock thisStock = stocks.get(symbolSearch);


        CharSequence qty = ((TextView) findViewById(R.id.qty)).getText();
        int addQty = 0;
        if (qty.length() != 0) {
            addQty = Integer.parseInt(qty.toString());
        } else {
            Toast.makeText(this, "Enter a Numeric Quantity", Toast.LENGTH_SHORT).show();
            return;

        }
        if (thisStock != null) {
            thisStock.setQuantity(thisStock.getQuantity() + addQty);
            switch (symbolSearch) {
                case "AMZN":
                    findViewById(R.id.Price).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.Quantity)).setText(thisStock.getQuantity() + " " + symbolSearch);
                    findViewById(R.id.Quantity).setVisibility(View.VISIBLE);
                    findViewById(R.id.Change).setVisibility(View.VISIBLE);
                    findViewById(R.id.Name).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.Value)).setText(String.format("Value: %01.2f", thisStock.getValue() / 100.0f));
                    findViewById(R.id.Value).setVisibility(View.VISIBLE);
                    findViewById(R.id.Delete).setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Amazon Stock Added", Toast.LENGTH_SHORT).show();

                    break;

                default:
                    Toast.makeText(this, "Invalid Stock Option", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {

            Toast.makeText(this, "Invalid Stock Option", Toast.LENGTH_SHORT).show();
        }


    }

    public void onnDelete(View view) {

        //find in array list
        String symbolSearch = view.getTag().toString();
        Stock thisStock = stocks.get(symbolSearch);

        //remove from array list
        Log.d(TAG, "clicked Delete on" + thisStock.getName());

       //tell adapter to update
    }


    public void Refresh(View v) {
//        findViewById(R.id.amznPrice).setVisibility(View.VISIBLE);
//        findViewById(R.id.amznQuantity).setVisibility(View.VISIBLE);
//        findViewById(R.id.amznChange).setVisibility(View.VISIBLE);
//        findViewById(R.id.amznName).setVisibility(View.VISIBLE);
//        findViewById(R.id.amznValue).setVisibility(View.VISIBLE);
//        findViewById(R.id.amznDelete).setVisibility(View.VISIBLE);
//        findViewById(R.id.msftPrice).setVisibility(View.VISIBLE);
//        findViewById(R.id.msftQuantity).setVisibility(View.VISIBLE);
//        findViewById(R.id.msftChange).setVisibility(View.VISIBLE);
//        findViewById(R.id.msftName).setVisibility(View.VISIBLE);
//        findViewById(R.id.msftValue).setVisibility(View.VISIBLE);
//        findViewById(R.id.msftDelete).setVisibility(View.VISIBLE);
//        findViewById(R.id.nintPrice).setVisibility(View.VISIBLE);
//        findViewById(R.id.nintQuantity).setVisibility(View.VISIBLE);
//        findViewById(R.id.nintChange).setVisibility(View.VISIBLE);
//        findViewById(R.id.nintName).setVisibility(View.VISIBLE);
//        findViewById(R.id.nintValue).setVisibility(View.VISIBLE);
//        findViewById(R.id.nintDelete).setVisibility(View.VISIBLE);
//        findViewById(R.id.leafPrice).setVisibility(View.VISIBLE);
//        findViewById(R.id.leafQuantity).setVisibility(View.VISIBLE);
//        findViewById(R.id.leafChange).setVisibility(View.VISIBLE);
//        findViewById(R.id.leafName).setVisibility(View.VISIBLE);
//        findViewById(R.id.leafValue).setVisibility(View.VISIBLE);
//        findViewById(R.id.leafDelete).setVisibility(View.VISIBLE);
//        findViewById(R.id.netfPrice).setVisibility(View.VISIBLE);
//        findViewById(R.id.netfQuantity).setVisibility(View.VISIBLE);
//        findViewById(R.id.netfChange).setVisibility(View.VISIBLE);
//        findViewById(R.id.netfName).setVisibility(View.VISIBLE);
//        findViewById(R.id.netfValue).setVisibility(View.VISIBLE);
//        findViewById(R.id.netfDelete).setVisibility(View.VISIBLE);
    }


    public void showStock(View view) {
        String symbolSearch = view.getTag().toString();
        Stock thisStock = stocks.get(symbolSearch);
        Intent i = new Intent(this, StockDetail.class);
        Bundle b = new Bundle();
        b.putString("name", thisStock.getName());
        b.putString("symbol", thisStock.getSymbol());
        b.putInt("price", thisStock.getPrice());
        b.putInt("change", thisStock.getChange());
        b.putInt("high", thisStock.getHigh());
        b.putInt("low", thisStock.getLow());
        b.putInt("volume", thisStock.getVolume());
        b.putInt("descID", thisStock.getDescID());
        b.putBoolean("more", displayMore);
        i.putExtras(b);
        startActivity(i);
    }

    public void showSettings(View view) {
        Intent i = new Intent(this, Settings.class);

        i.putExtra("displayMore", displayMore);
        startActivityForResult(i, MY_ID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent i) {

        Log.d(TAG, "onActivityResult()");


        if ((requestCode == MY_ID) && (resultCode == RESULT_OK)) {

            // check for data that may have returned from the second activity

            if (i.hasExtra("displayMore")) {
                displayMore = i.getExtras().getBoolean("displayMore");
            }

        }

        super.onActivityResult(requestCode, resultCode, i);
    }
}
