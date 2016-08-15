package net.greenrivertech.jschwarzwalder.stockpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {



    final static int MY_ID = 434;

    private final static String TAG = MainActivity.class.getSimpleName();

    HashMap<String, Stock> stocks = new HashMap<String, Stock>();
    ArrayList<Stock> stocksDisplay = new ArrayList<Stock>();
    BaseAdapter stockAdapter;
    int stockTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_portfolio);
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
        stockTotal = 0;


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
        

        final ListView stocksList = (ListView) findViewById(R.id.stockListDisplay);

        stockAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return stocksDisplay.size();
            }

            @Override
            public Object getItem(int position) {
                return stocksDisplay.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null){
                    convertView = View.inflate(MainActivity.this, R.layout.stock_list_item, null);
                }
                Stock thisStock = (Stock) stocksDisplay.get(position);

                ((TextView) convertView.findViewById(R.id.Price)).setText(String.format("%01.2f", thisStock.getPrice() / 100.0f));
                ((TextView) convertView.findViewById(R.id.Price)).setTag(thisStock.getSymbol());


                ((TextView) convertView.findViewById(R.id.Quantity)).setText(thisStock.getQuantity() + " " + thisStock.getSymbol());
                ((TextView) convertView.findViewById(R.id.Quantity)).setTag(thisStock.getSymbol());

                TextView changeView = (TextView) convertView.findViewById(R.id.Change);
                changeView.setText(String.format("(%01.2f)", thisStock.getChange() / 100.0f));
                if (thisStock.getChange() < 0){
                    changeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
                } else {
                    changeView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
                }
                changeView.setTag(thisStock.getSymbol());
                changeView.setVisibility(View.VISIBLE);

                ((TextView) convertView.findViewById(R.id.Name)).setText(thisStock.getName());
                ((TextView) convertView.findViewById(R.id.Name)).setTag(thisStock.getSymbol());

                ((TextView) convertView.findViewById(R.id.Value)).setText(String.format("Value: %01.2f", thisStock.getValue() / 100.0f));
                ((TextView) convertView.findViewById(R.id.Value)).setTag(thisStock.getSymbol());

                convertView.findViewById(R.id.Delete).setTag(thisStock.getSymbol());
                convertView.findViewById(R.id.Delete).setVisibility(View.VISIBLE);

                Log.d("StockPro", thisStock.getName());


                return convertView;
            }


        };

        stocksList.setAdapter(stockAdapter);
        stocksList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id){
                showStock(view);
            }
        });

        //update Total Value and refresh the display
        updateTotal();
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
            if (thisStock.getQuantity() == 0)
            {
                stocksDisplay.add(thisStock);
            }
            thisStock.setQuantity(thisStock.getQuantity() + addQty);
            stockAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Added " + addQty + " " + thisStock.getName(), Toast.LENGTH_SHORT).show();
            Log.d("STOCK PRO", stocksDisplay.toString());

        } else {

            Toast.makeText(this, "Invalid Stock Option", Toast.LENGTH_SHORT).show();
        }

        //update Total Value and refresh the display
        updateTotal();

        //saves current qty in internal Storage
        saveDetails();
    }

    public void onDelete(View view) {

        //find stock
        String symbolSearch = view.getTag().toString();
        Stock thisStock = stocks.get(symbolSearch);

        //remove from array list
        Log.d(TAG, "clicked Delete on" + thisStock.getName());
        stocksDisplay.remove(thisStock);
        thisStock.setQuantity(0);

       //tell adapter to update
        stockAdapter.notifyDataSetChanged();

        //update Total Value and refresh the display
        updateTotal();

        //saves current qty in internal Storage
        saveDetails();
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

        i.putExtras(b);
        startActivity(i);
    }

    public void showSettings(View view) {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    private void updateTotal(){
        //clear previous value
        stockTotal = 0;

        //add value for each stock in the Display Array
        for (Stock thisStock : stocksDisplay  ){
            stockTotal += thisStock.getValue();

        }

        //Change view to display newly calculated value
        ((TextView) findViewById(R.id.totalValue)).setText(String.format("Total Value: %01.2f", stockTotal / 100.0f));

    }

    private void saveDetails () {
        try {

            FileOutputStream savedFile = openFileOutput("qtyDetails.txt", Context.MODE_PRIVATE);
           OutputStreamWriter dataStore = new OutputStreamWriter(savedFile);
            for (Stock thisStock : stocksDisplay  ){
                String symbol = thisStock.getSymbol();
                int qty = thisStock.getQuantity();
                String save = "" + symbol + ", " + qty + "\n";
                dataStore.write(save);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
