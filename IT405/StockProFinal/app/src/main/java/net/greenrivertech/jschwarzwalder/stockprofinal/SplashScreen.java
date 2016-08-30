package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.content.Intent;
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

        //wait 10 seconds

        //advance to next screen
		showMain();
    }

      public void showMain() {
        Intent i = new Intent(this, MainScreen.class);
        startActivity(i);
    }
}     