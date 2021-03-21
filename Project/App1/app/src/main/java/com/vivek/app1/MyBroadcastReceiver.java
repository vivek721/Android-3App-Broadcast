package com.vivek.app1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getStringExtra("url");
        Log.i("App1_MyBroadcastReceiver", "onReceive: " + url);

        //Intent to open TvshowActivity when receives receives the broadcast
        Intent i = new Intent();
        i.setClassName("com.vivek.app1", "com.vivek.app1.TvshowActivity");
        i.putExtra("url", url);
        context.startActivity(i);
    }
}
