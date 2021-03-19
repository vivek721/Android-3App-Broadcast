package com.vivek.app1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println(intent);
        Intent i = new Intent();
        i.setClassName("com.vivek.app1", "com.vivek.app1.TvshowActivity");
        context.startActivity(i);
    }
}
