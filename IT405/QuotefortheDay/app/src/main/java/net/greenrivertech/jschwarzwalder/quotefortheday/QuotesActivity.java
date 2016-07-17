package net.greenrivertech.jschwarzwalder.quotefortheday;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class QuotesActivity extends AppCompatActivity {


    private int[] randomQuote = new int[10];
    private TextView quote = null;
    private int[] randomColor = new int[13];
    private int color = 0;
    private final static String TAG = QuotesActivity.class.getSimpleName();
    private Random numGen = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes);

        // change the text using a string defined in the strings.xml
        quote = (TextView) findViewById(R.id.quote);

        if (quote != null) {
            quote.setText(getString(R.string.welcome));
        }

        Log.d(TAG, getString(R.string.welcome));

        randomColor[0]  = R.color.tangerine ;
        randomColor[1]  = R.color.murkyWater ;
        randomColor[2]  = R.color.bubblegum ;
        randomColor[3]  = R.color.pipi ;
        randomColor[4]  = R.color.ice ;
        randomColor[5]  = R.color.white ;
        randomColor[6]  = R.color.seattleSky ;
        randomColor[7]  = R.color.emerald ;
        randomColor[8]  = R.color.cornflower ;
        randomColor[9]  = R.color.powder ;
        randomColor[10] = R.color.winter ;
        randomColor[11] = R.color.lilac;
        randomColor[12] = R.color.plum ;


        Log.d(TAG, "color array has been built");
        Log.d(TAG, randomColor.toString());

        for (int i = 1; i <= 10; i++) {
            String quoteString = "quote" + i;
            randomQuote[i-1] = getResources().getIdentifier(quoteString, "string", getPackageName());
            Log.d(TAG, "Quote " + i + " has been added");
        }
        Button btn = (Button)findViewById(R.id.button);

        btn.setOnClickListener (new View.OnClickListener() {
            public void onClick(View view) {

                Log.d(TAG, "onButtonClick()");

                int colorIndex =  numGen.nextInt(randomColor.length);;
                int quoteIndex =  numGen.nextInt(randomQuote.length);

                Toast.makeText(getBaseContext(), "Changing Quote for the Day", Toast.LENGTH_SHORT).show();

                if (quote != null) {
                    quote.setText(randomQuote[quoteIndex]);
                    //Log.d(TAG, quote.getString());
                    quote.setTextColor(getResources().getColor(randomColor[colorIndex]));
                    if (quoteIndex == 9){
                        quote.setTextSize(30);
                    } else if (quoteIndex == 3 || quoteIndex == 8) {
                        quote.setTextSize(35);
                    } else {
                        quote.setTextSize(40);
                    }
                }

            }

        });


    }



}
