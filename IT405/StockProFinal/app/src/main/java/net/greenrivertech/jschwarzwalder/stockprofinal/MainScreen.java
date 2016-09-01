package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Button stockQuotesButton = (Button) findViewById(R.id.StockQuotes);
        Button managePorfolioButton = (Button) findViewById(R.id.Portfolio);
        Button settingsButton = (Button) findViewById(R.id.Settings);

        View.OnClickListener quotesListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent q = new Intent(MainScreen.this, StockQuotesActivity.class);
                startActivity(q);
            }
        };
        View.OnClickListener portfolioListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent q = new Intent(MainScreen.this, ManagePortfolioActivity.class);
                startActivity(q);
            }
        };
        View.OnClickListener settingsListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent q = new Intent(MainScreen.this, SettingsActivity.class);
                startActivity(q);
            }
        };

        managePorfolioButton.setOnClickListener(portfolioListener);
        stockQuotesButton.setOnClickListener(quotesListener);
        settingsButton.setOnClickListener(settingsListener);
    }
}
