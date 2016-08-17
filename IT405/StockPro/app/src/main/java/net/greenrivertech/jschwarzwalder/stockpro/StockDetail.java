package net.greenrivertech.jschwarzwalder.stockpro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StockDetail extends AppCompatActivity {

    private String symbol;
    private String name;
    private int price;
    private int change;
    private int high;
    private int low;
    private int volume;
    private boolean displayMore;
    private String desc;
    private boolean descView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_quotes);
        SharedPreferences settings =  PreferenceManager.getDefaultSharedPreferences(this);
        displayMore = settings.getBoolean(Settings.PREF_DISPLAY, true);
        if (savedInstanceState == null) {
            savedInstanceState = getIntent().getExtras();
        }
        setFields(savedInstanceState);
    }


    @Override
    protected void onResume(){
        super.onResume();
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

        if (displayMore == false)
        {
            findViewById(R.id.moreDetail).setVisibility(View.INVISIBLE);
        } else {
            findViewById(R.id.moreDetail).setVisibility(View.VISIBLE);
        }

        TextView newDesc = (TextView)findViewById(R.id.desc);
        newDesc.setText(desc);
    }

    public void backtoList(View view){
//        Intent i = new Intent (this, MainActivity.class);
//        startActivity(i);
        finish();
    }

    public void displayMore(View view){
        displayMore();

        descView = true;
    }

    public void displayMore() {
        findViewById(R.id.symbol).setVisibility(View.GONE);
        findViewById(R.id.high).setVisibility(View.GONE);
        findViewById(R.id.low).setVisibility(View.GONE);
        findViewById(R.id.volume).setVisibility(View.GONE);
        findViewById(R.id.change).setVisibility(View.GONE);
        findViewById(R.id.price).setVisibility(View.GONE);


        findViewById(R.id.desc).setVisibility(View.VISIBLE);

        findViewById(R.id.moreDetail).setVisibility(View.GONE);
        findViewById(R.id.lessDetail).setVisibility(View.VISIBLE);
    }

    public void displayLess(View view) {
        findViewById(R.id.symbol).setVisibility(View.VISIBLE);
        findViewById(R.id.high).setVisibility(View.VISIBLE);
        findViewById(R.id.low).setVisibility(View.VISIBLE);
        findViewById(R.id.volume).setVisibility(View.VISIBLE);
        findViewById(R.id.change).setVisibility(View.VISIBLE);
        findViewById(R.id.price).setVisibility(View.VISIBLE);


        findViewById(R.id.desc).setVisibility(View.GONE);

        findViewById(R.id.moreDetail).setVisibility(View.VISIBLE);
        findViewById(R.id.lessDetail).setVisibility(View.GONE);

        descView = false;
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

        descView = savedInstanceState.getBoolean("descView");

        if (descView)
        {
            displayMore();
        }
    }

}
