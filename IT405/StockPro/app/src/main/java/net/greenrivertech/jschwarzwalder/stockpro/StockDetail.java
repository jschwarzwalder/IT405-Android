package net.greenrivertech.jschwarzwalder.stockpro;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class StockDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stock_quotes);
    }


    @Override
    protected void onResume(){
        super.onResume();
        Bundle b = getIntent().getExtras();
        String symbol = b.getString("symbol");
        String name = b.getString("name");
        int price = b.getInt("price");
        int change = b.getInt("change");
        int high = b.getInt("high");
        int low = b.getInt("low");
        int volume = b.getInt("volume");
        boolean more = b.getBoolean("more");

        TextView newSymbol = (TextView)findViewById(R.id.symbol);
        newSymbol.setText("SYMBOL: " + symbol);


        TextView newName = (TextView)findViewById(R.id.name);
        newName.setText(name);

        TextView newPrice = (TextView)findViewById(R.id.price);
        newPrice.setText(String.format("Current Price: %d.%02d", price / 100, price % 100));

        TextView newChange = (TextView)findViewById(R.id.change);
        newChange.setText(String.format("Change: %+d.%02d", change / 100, change % 100));
        if (change >= 0){
            newChange.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.green));
        } else {
            newChange.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.orange));
        }

        TextView newHigh = (TextView)findViewById(R.id.high);
        newHigh.setText(String.format("52 Week High: %d.%02d", high / 100, high % 100));

        TextView newLow = (TextView)findViewById(R.id.low);
        newLow.setText(String.format("52 Week Low: %d.%02d", low / 100, low % 100));

        TextView newVolume = (TextView)findViewById(R.id.volume);
        newVolume.setText(String.format("Volume: %d", volume));


    }
}
