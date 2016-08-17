package net.greenrivertech.jschwarzwalder.stockpro;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {


    final static int MY_ID = 434;
    final static String ASSET_FILE = "stocks.csv";

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
        try {
            //Read from stocks.csv file
            AssetManager assetManager = getAssets();

            InputStream stockFile = assetManager.open(ASSET_FILE);
            Scanner readStocks = new Scanner(stockFile);
            readStocks.nextLine(); //skip header
            while (readStocks.hasNextLine()) {
                String stockdata = readStocks.nextLine();
                String[] data = stockdata.split(",");
                String desc = "";
                for (int i = 6; i < data.length-1; i++){
                    desc += data[i].trim() + ", ";
                }
                desc += data[data.length].trim();

                Stock tempStock = new Stock(
                        data[0].trim(), //symbol,
                        data[1].trim(), //name,
                        Integer.parseInt(data[2]), //price
                        Integer.parseInt(data[3]), //change,
                        Integer.parseInt(data[4]), //high,
                        Integer.parseInt(data[5]), //low,
                        Integer.parseInt(data[6]), //volume,
                        desc//desc
                );

                stocks.put(data[0].toUpperCase(), tempStock);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Asset File not Found", Toast.LENGTH_SHORT).show();
        }


        //restore user inputted data from  qtyDetails.txt
        try {
            FileInputStream userInput = openFileInput("qtyDetails.txt");
            Scanner prevAdd = new Scanner(userInput);
            while (prevAdd.hasNextLine()) {
                String storedStock = prevAdd.nextLine();
                String[] data = storedStock.split(",");

                Stock thisStock = stocks.get(data[0]);
                if (Integer.parseInt(data[1]) > 0) {
                    stocksDisplay.add(thisStock);
                    thisStock.setQuantity(Integer.parseInt(data[1]));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
        }

        //update Total Value and refresh the display
        updateTotal();

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
                if (convertView == null) {
                    convertView = View.inflate(MainActivity.this, R.layout.stock_list_item, null);
                }
                Stock thisStock = (Stock) stocksDisplay.get(position);

                ((TextView) convertView.findViewById(R.id.Price)).setText(String.format("%01.2f", thisStock.getPrice() / 100.0f));
                ((TextView) convertView.findViewById(R.id.Price)).setTag(thisStock.getSymbol());


                ((TextView) convertView.findViewById(R.id.Quantity)).setText(thisStock.getQuantity() + " " + thisStock.getSymbol());
                ((TextView) convertView.findViewById(R.id.Quantity)).setTag(thisStock.getSymbol());

                TextView changeView = (TextView) convertView.findViewById(R.id.Change);
                changeView.setText(String.format("(%01.2f)", thisStock.getChange() / 100.0f));
                if (thisStock.getChange() < 0) {
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
        stocksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
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
            if (thisStock.getQuantity() == 0) {
                stocksDisplay.add(thisStock);
            }
            thisStock.setQuantity(thisStock.getQuantity() + addQty);
            stockAdapter.notifyDataSetChanged();
            Toast.makeText(this, "Added " + addQty + " " + thisStock.getName(), Toast.LENGTH_SHORT).show();
            Log.d("STOCK PRO", stocksDisplay.toString());

        } else {

            Toast.makeText(this, "Invalid Stock Option", Toast.LENGTH_SHORT).show();
        }

        //saves current qty in internal Storage
        saveDetails();

        //update Total Value and refresh the display
        updateTotal();


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
        b.putString("desc", thisStock.getDesc());

        i.putExtras(b);
        startActivity(i);
    }

    public void showSettings(View view) {
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    private void updateTotal() {
        //clear previous value
        stockTotal = 0;

        //add value for each stock in the Display Array
        for (Stock thisStock : stocksDisplay) {
            stockTotal += thisStock.getValue();

        }

        //Change view to display newly calculated value
        ((TextView) findViewById(R.id.totalValue)).setText(String.format("Total Value: %01.2f", stockTotal / 100.0f));

    }

    private void saveDetails() {
        try {

            FileOutputStream savedFile = openFileOutput("qtyDetails.txt", Context.MODE_PRIVATE);
            OutputStreamWriter dataStore = new OutputStreamWriter(savedFile);
            for (Stock thisStock : stocksDisplay) {
                String symbol = thisStock.getSymbol();
                int qty = thisStock.getQuantity();
                String save = "" + symbol + ", " + qty + "\n";
                dataStore.write(save);
                Toast.makeText(this, symbol + " saved", Toast.LENGTH_SHORT).show();

            }
            dataStore.close();
            String name = getFilesDir().getAbsolutePath();
            Toast.makeText(this, "File stored as " + name, Toast.LENGTH_LONG).show();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "IOExecption", Toast.LENGTH_SHORT).show();
        }
    }

}
