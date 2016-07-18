package net.greenrivertech.jschwarzwalder.stocklayouts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        sync_box.setBackgroundColor(R.color.teal);
    }
}
