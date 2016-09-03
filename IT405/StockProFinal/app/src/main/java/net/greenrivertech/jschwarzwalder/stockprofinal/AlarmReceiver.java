package net.greenrivertech.jschwarzwalder.stockprofinal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Eiseldora on 9/2/2016.
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent background = new Intent(context, BackgroundStockService.class);
        context.startService(background);
    }

}
