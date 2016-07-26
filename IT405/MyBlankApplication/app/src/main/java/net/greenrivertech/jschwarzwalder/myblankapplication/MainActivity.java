package net.greenrivertech.jschwarzwalder.myblankapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    final static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() has been activated");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d(TAG, "onRestart() has been activated");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() has been activated");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() has been activated");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() has been activated");
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestory() has been activated");
    }


}
