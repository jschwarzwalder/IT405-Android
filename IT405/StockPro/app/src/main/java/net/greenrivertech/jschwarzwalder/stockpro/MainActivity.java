package net.greenrivertech.jschwarzwalder.stockpro;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    boolean displayMore = true;
    private final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_portfolio);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.abs_layout);
    }
    public void onAmazonDelete(View v) {
        Log.d(TAG, "clicked Amazon Delete");

        findViewById(R.id.amznPrice).setVisibility(View.GONE);
        findViewById(R.id.amznQuantity).setVisibility(View.GONE);
        findViewById(R.id.amznChange).setVisibility(View.GONE);
        findViewById(R.id.amznName).setVisibility(View.GONE);
        findViewById(R.id.amznValue).setVisibility(View.GONE);
        findViewById(R.id.amznDelete).setVisibility(View.GONE);
    }
    protected void onResume(Bundle savedInstanceState){
        final Button deleteAmazonButton = (Button) findViewById(R.id.amznDelete);
        deleteAmazonButton.setOnClickListener(new View.OnClickListener() {

        });

        final Button deleteMicrosoftButton = (Button) findViewById(R.id.msftDelete);
        deleteMicrosoftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "clicked Microsoft Delete");

                findViewById(R.id.msftPrice).setVisibility(View.GONE);
                findViewById(R.id.msftQuantity).setVisibility(View.GONE);
                findViewById(R.id.msftChange).setVisibility(View.GONE);
                findViewById(R.id.msftName).setVisibility(View.GONE);
                findViewById(R.id.msftValue).setVisibility(View.GONE);
                findViewById(R.id.msftDelete).setVisibility(View.GONE);
            }
        });

        final Button deleteNetflixButton = (Button) findViewById(R.id.netfDelete);
        deleteNetflixButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "clicked Netflix Delete");

                findViewById(R.id.netfPrice).setVisibility(View.GONE);
                findViewById(R.id.netfQuantity).setVisibility(View.GONE);
                findViewById(R.id.netfChange).setVisibility(View.GONE);
                findViewById(R.id.netfName).setVisibility(View.GONE);
                findViewById(R.id.netfValue).setVisibility(View.GONE);
                findViewById(R.id.netfDelete).setVisibility(View.GONE);
            }
        });

        final Button deleteSpringLeafButton = (Button) findViewById(R.id.leafDelete);
        deleteSpringLeafButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "clicked Leaf Delete");

                findViewById(R.id.leafPrice).setVisibility(View.GONE);
                findViewById(R.id.leafQuantity).setVisibility(View.GONE);
                findViewById(R.id.leafChange).setVisibility(View.GONE);
                findViewById(R.id.leafName).setVisibility(View.GONE);
                findViewById(R.id.leafValue).setVisibility(View.GONE);
                findViewById(R.id.leafDelete).setVisibility(View.GONE);
            }
        });

        final Button deleteNintendoButton = (Button) findViewById(R.id.nintDelete);
        deleteNintendoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d(TAG, "clicked Nintendo Delete");

                findViewById(R.id.nintPrice).setVisibility(View.GONE);
                findViewById(R.id.nintQuantity).setVisibility(View.GONE);
                findViewById(R.id.nintChange).setVisibility(View.GONE);
                findViewById(R.id.nintName).setVisibility(View.GONE);
                findViewById(R.id.nintValue).setVisibility(View.GONE);
                findViewById(R.id.nintDelete).setVisibility(View.GONE);
            }
        });


    }
}
