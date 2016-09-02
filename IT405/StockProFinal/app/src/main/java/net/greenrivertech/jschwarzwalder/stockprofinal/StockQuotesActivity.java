package net.greenrivertech.jschwarzwalder.stockprofinal;

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

public class StockQuotesActivity extends AppCompatActivity {

    private String symbol;
    private String name;
    private int price;
    private int change;
    private int high;
    private int low;
    private int volume;
    private String desc;
    private boolean descView;

    StockService stockService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_quotes);
        stockService = new StockService();
    }

    private void setFields(Bundle b) {
        symbol = b.getString("symbol");
        name = b.getString("name");
        price = b.getInt("price");
        change = b.getInt("change");
        high = b.getInt("high");
        low = b.getInt("low");
        volume = b.getInt("volume");
        desc = b.getString("desc");


        TextView newSymbol = (TextView)findViewById(R.id.symbol);
        newSymbol.setText("SYMBOL: " + symbol);


        TextView newName = (TextView)findViewById(R.id.name);
        newName.setText(name);

        TextView newPrice = (TextView)findViewById(R.id.price);
        newPrice.setText(String.format("Current Price: %d.%02d", price / 100, price % 100));

        TextView newChange = (TextView)findViewById(R.id.change);
        if (change >= 0){
            newChange.setText(String.format("Change: +%d.%02d", change / 100, change % 100));
            newChange.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        } else {
            newChange.setText(String.format("Change: -%d.%02d", change / 100, Math.abs(change % 100)));
            newChange.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
        }

        TextView newHigh = (TextView)findViewById(R.id.high);
        newHigh.setText(String.format("52 Week High: %d.%02d", high / 100, high % 100));

        TextView newLow = (TextView)findViewById(R.id.low);
        newLow.setText(String.format("52 Week Low: %d.%02d", low / 100, low % 100));

        TextView newVolume = (TextView)findViewById(R.id.volume);
        newVolume.setText(String.format("Volume: %d", volume));


        TextView newDesc = (TextView)findViewById(R.id.desc);
        newDesc.setText(desc);
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

        new FetchStockInfofromInternet().execute(symbolSearch);



    }

    @Override
    protected void onSaveInstanceState(Bundle b)
    {

        b.putString("symbol", symbol);
        b.putString("name", name);
        b.putInt("price", price);
        b.putInt("change", change);
        b.putInt("high", high);
        b.putInt("low", low);
        b.putInt("volume", volume);
        b.putString("desc", desc);
        b.putBoolean("descView", descView);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        setFields(savedInstanceState);
    }


    // Async Task Class
    class FetchStockInfofromInternet extends AsyncTask<String, String, String> {

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
            if ((nl!=null) && (nl.size() > 0)) {
//                al.clear();
//                al.addAll(nl);
//                myAdapter.notifyDataSetChanged();

                findViewById(R.id.symbol).setVisibility(View.VISIBLE);
                findViewById(R.id.name).setVisibility(View.VISIBLE);
                findViewById(R.id.price).setVisibility(View.VISIBLE);
                findViewById(R.id.prevDayClosePrice).setVisibility(View.VISIBLE);
                findViewById(R.id.priceAtOpening).setVisibility(View.VISIBLE);
                findViewById(R.id.lastPurchased).setVisibility(View.VISIBLE);
                findViewById(R.id.change).setVisibility(View.VISIBLE);
                findViewById(R.id.changePercent).setVisibility(View.VISIBLE);
                findViewById(R.id.high).setVisibility(View.VISIBLE);
                findViewById(R.id.low).setVisibility(View.VISIBLE);
                findViewById(R.id.yearRange).setVisibility(View.VISIBLE);
                findViewById(R.id.earningsPerShare).setVisibility(View.VISIBLE);
                findViewById(R.id.priceToEarningsRatio).setVisibility(View.VISIBLE);
                findViewById(R.id.volume).setVisibility(View.VISIBLE);
                findViewById(R.id.qty).setVisibility(View.VISIBLE);
                findViewById(R.id.addButton).setVisibility(View.VISIBLE);

            }
            else {

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

//            CharSequence qty = ((TextView) findViewById(R.id.qty)).getText();
//            int addQty = 0;
//            if (qty.length() != 0) {
//                addQty = Integer.parseInt(qty.toString());
//            } else {
//                Toast.makeText(this, "Enter a Numeric Quantity", Toast.LENGTH_SHORT).show();
//                return;
//
//            }
//
//            if (thisStock != null) {
//                if (thisStock.getQuantity() == 0) {
//                    stocksDisplay.add(thisStock);
//                }
//                thisStock.setQuantity(thisStock.getQuantity() + addQty);
//                stockAdapter.notifyDataSetChanged();
//                Toast.makeText(this, "Added " + addQty + " " + thisStock.getName(), Toast.LENGTH_SHORT).show();
//                Log.d("STOCK PRO", stocksDisplay.toString());
//
//            } else {
//
//                Toast.makeText(this, "Invalid Stock Option", Toast.LENGTH_SHORT).show();
//            }
//
//            //saves current qty in internal Storage
//            saveDetails();
//
//            //update Total Value and refresh the display
//            updateTotal();

        }
    }
}
