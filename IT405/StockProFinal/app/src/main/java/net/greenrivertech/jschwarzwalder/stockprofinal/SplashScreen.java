package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        View.OnClickListener screen = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //advance to next screen
				showMain();
            }
        };
        View layout = findViewById(R.id.splash);
        layout.setOnClickListener(screen);
    }

    @Override
    protected void onResume(){
        super.onResume();

        new UpdateDatabase().execute();

        //wait 10 seconds
        //advance to next screen
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final Intent mainIntent = new Intent(SplashScreen.this, MainScreen.class);
                SplashScreen.this.startActivity(mainIntent);
                SplashScreen.this.finish();
            }
        }, 10000);



    }

      public void showMain() {
        Intent i = new Intent(this, MainScreen.class);
        startActivity(i);
    }

    private class UpdateDatabase extends AsyncTask<Void, Void, Void>
    {

        @Override
        protected Void doInBackground(Void... voids) {
            SQLiteDatabase db = new StocksDatabaseHelper(SplashScreen.this).getWritableDatabase();
            return null;
        }
    }
}     