package net.greenrivertech.jschwarzwalder.stockpro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class Settings extends AppCompatActivity {

    private boolean displayMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }

    @Override
    protected void onResume() {
        super.onResume();

        displayMore = getIntent().getBooleanExtra("displayMore", true);
        final CheckBox checkbox = (CheckBox) findViewById(R.id.displayMoreBox);
        checkbox.setChecked(displayMore);
    }

    public void backtoList(View view) {
//        Intent i = new Intent (this, MainActivity.class);
//        startActivity(i);
        finish();
    }

    public void onDisplayMoreButtonClicked(View view) {
        final CheckBox checkbox = (CheckBox) view;

        if (checkbox.isChecked()) {
            displayMore = true;
        } else {
            displayMore = false;
        }

        System.out.println(displayMore);
    }

    public void onBackgroundSyncClicked(View view) {
    }

    @Override
    public void finish() {
        Intent i = new Intent();
        i.putExtra("displayMore", displayMore);

        setResult(RESULT_OK, i);
        super.finish();
    }
}
